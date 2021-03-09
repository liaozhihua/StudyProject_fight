package com.xm.module_compiler;

import com.google.auto.service.AutoService;
import com.xm.module_annotation.BindViewClick;
import com.xm.module_annotation.BindViewId;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

//主要是用来自动生成 META-INF 信息 不然会导致java代码没法生成
//需要依赖api 'com.google.auto.service:auto-service:1.0-rc2'库
@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    /**
     * 一个需要生成的类的集合（key为类的全名，value为该类所有相关的需要的信息）
     */
    private Map<String, BindProxy> mProxyMap = new HashMap<String, BindProxy>();
    private Filer environmentFiler;
    private Elements elementUtils;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        //ProcessingEnvironment跟RoundEnvironment
        // 参考 https://juejin.cn/post/6844903458206646280
        super.init(processingEnvironment);
        //获取跟文件相关的辅助类
        environmentFiler = processingEnvironment.getFiler();
        //获取跟元素相关的辅助类
        elementUtils = processingEnvironment.getElementUtils();
        //获取跟日志相关的辅助类
        messager = processingEnvironment.getMessager();
    }

    //这个是核心方法
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //避免该方法多次调用导致生成重复类
        mProxyMap.clear();
        collectionInfo(roundEnvironment);
        generateClass();
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
//        return super.getSupportedSourceVersion();//默认是jdk6
        //这个方法是干嘛用的 参考https://www.open-open.com/lib/view/open1470735314518.html
        return SourceVersion.RELEASE_8;
    }

    //支持的注解类型
    @Override
    public Set<String> getSupportedAnnotationTypes() {
//        return super.getSupportedAnnotationTypes();
        //linkedHashSet HashSet hashMap 等等的区别
        //参考 https://russxia.com/2019/04/29/TreeSet-LinkedHashSet-HashSet%E7%9A%84%E5%8C%BA%E5%88%AB/
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        //添加自己支持的注解
        //getCanonicalName getName getTypeName getSimpleName区别
        // 参考https://www.cnblogs.com/maokun/p/6771365.html
        linkedHashSet.add(BindViewId.class.getCanonicalName());
        linkedHashSet.add(BindViewClick.class.getCanonicalName());
        return linkedHashSet;
    }

    //遍历循环获取类中所有信息 收集类信息
    private void collectionInfo(RoundEnvironment roundEnvironment) {
        //获取被该注解声明的类跟变量(如果该注解使用在方法上 也会获取到被该注解声明的方法)
        Set<? extends Element> elements = roundEnvironment
                .getElementsAnnotatedWith(BindViewId.class);
        //ElementKind
        // 参考1 https://blog.csdn.net/u010126792/article/details/97612680
        //参考2  https://blog.csdn.net/u010126792/article/details/95614328
        for (Element element : elements) {
            //如果该注解使用在类上
            if (element.getKind() == ElementKind.CLASS) {
                //获取完整规范的包名
                String qualifiedName = elementUtils.getPackageOf(element).getQualifiedName().toString();
                BindViewId elementAnnotation = element.getAnnotation(BindViewId.class);
                BindProxy bindProxy1 = mProxyMap.get(qualifiedName);
                if (bindProxy1 == null) {
                    bindProxy1 = new BindProxy();
                    mProxyMap.put(qualifiedName, bindProxy1);
                }
                //再放入类信息
                bindProxy1.typeElement = (TypeElement) element;
                bindProxy1.value = elementAnnotation.value();
                bindProxy1.packageName = qualifiedName;
            } else if (element.getKind() == ElementKind.FIELD) {
                //如果注解是在成员变量上面
                //getEnclosingElement() 获取该元素的父元素,如果是PackageElement则返回null，
                // 如果是TypeElement则返回PackageElement，如果是TypeParameterElement则返回泛型Element
                TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
                String qualifiedName = enclosingElement.getQualifiedName().toString();
                BindProxy bindProxy = mProxyMap.get(qualifiedName);
                if (bindProxy == null) {
                    bindProxy = new BindProxy();
                    mProxyMap.put(qualifiedName, bindProxy);
                    bindProxy.packageName = qualifiedName;
                }
                BindViewId annotation = element.getAnnotation(BindViewId.class);
                bindProxy.mInjectElements.put(annotation.value(), (VariableElement) element);
            }
        }

        //上述是针对BindViewId.class使用在类上面或者成员变量上面
        //下面考虑使用在方法上面 判断被注解使用的方法
        Set<? extends Element> elementsMethod = roundEnvironment.getElementsAnnotatedWith(BindViewClick.class);
        for (Element element : elementsMethod) {
            //element.getKind() 参考https://blog.csdn.net/u010126792/article/details/97612680
            if (element.getKind() == ElementKind.METHOD) {
                //通过方法元素获取上层封装的类型 也就是类元素
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                String qualifiedName = typeElement.getQualifiedName().toString();
                BindProxy bindProxy = mProxyMap.get(qualifiedName);
                if (bindProxy == null) {
                    bindProxy = new BindProxy();
                    mProxyMap.put(qualifiedName, bindProxy);
                    bindProxy.packageName = qualifiedName;
                }
                //这块注意的是 针对于BindViewClick的值是一个集合 所以需要遍历放进mInjectMethods中
                BindViewClick annotation = element.getAnnotation(BindViewClick.class);
                int[] values = annotation.value();
                for (int i = 0; i < values.length; i++) {
                    bindProxy.mInjectMethods.put(values[i], (ExecutableElement) element);
                }
            }
        }
    }

    //生成真正意义上的代理类
    //需要用到初始化里面的辅助类
    private void generateClass() {
        Set<String> stringSet = mProxyMap.keySet();
        for (String set: stringSet) {
            BindProxy bindProxy = mProxyMap.get(set);
            if (bindProxy != null) {
                try {
                    //这块用到流的知识 这块不熟 后期再准备
                    JavaFileObject classFile = environmentFiler.createClassFile(bindProxy.getProxyClassFullName(), bindProxy.typeElement);
                    Writer writer = classFile.openWriter();
                    writer.write(bindProxy.generateJavaCode());
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
