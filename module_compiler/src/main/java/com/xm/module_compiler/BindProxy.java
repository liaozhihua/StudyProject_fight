package com.xm.module_compiler;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * 获取对应需要生成某个类的全部信息
 */
public class BindProxy {
    String packageName;

    //获取类元素
    TypeElement typeElement;
    //获取类元素的注解值
    int value;

    //获取成员变量集合 key就是成员变量注解的值
    //todo  为啥要采用map
    // VariableElement代表一个 字段, 枚举常量, 方法或者构造方法的参数, 局部变量及 异常参数等元素
    Map<Integer, VariableElement> mInjectElements = new HashMap<>();

    //获取方法集合
    Map<Integer, ExecutableElement> mInjectMethods = new HashMap<>();

    /**
     * 采用类名方式不能被混淆(否则编译阶段跟运行阶段，该字符串会不一样)，或者采用字符串方式
     */
    //todo 暂时不了解 也不处理
    public static final String PROXY = "TA";
    public static final String ClassSuffix = "_" + PROXY;
    public String getProxyClassFullName() {
        //getQualifiedName返回此类型元素的完全限定名称。
        // 更准确地说，返回规范 名称。对于没有规范名称的局部类和匿名类，返回一个空名称
        return typeElement.getQualifiedName().toString() + ClassSuffix;
    }
    public String getClassName() {
        //getSimpleName得到类的简写名称 也就是纯类名
        return typeElement.getSimpleName().toString() + ClassSuffix;
    }

    //手动编写会容易出错 如改变了module_api的前缀 就会导致加载失败
//    public String generateJavaCode() {
//        //StringBuffer跟StringBuilder区别 参考https://blog.csdn.net/weixin_30782293/article/details/97742549
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("//自动生成的注解类，勿动!!!\n")
//                //注意module_api前缀
//        .append("import com.xm.module_api.*;\n")
//        .append("import android.support.annotation.Keep;\n")
//        .append("import android.view.View;\n")
//        .append("import " + typeElement.getQualifiedName() + ";\n")
//        .append("\n")
//        .append("@Keep").append("\n")//禁止混淆，否则反射的时候找不到该类
//        .append("public class ").append(getClassName())
//        .append(" {\n");
//        generateMethodAndElemet(stringBuilder);
//        stringBuilder.append("}\n");
//        return stringBuilder.toString();
//    }
//
//    //针对方法跟成员变量的处理
//    private void generateMethodAndElemet(StringBuilder builder) {
//        builder.append("    public " + getClassName() + "(final " + typeElement.getSimpleName() + " host, View object) {\n");
//        if (value > 0) {
//            builder.append("        host.setContentView(" + value + ");\n");
//        }
//        //以下是直接copy其他优秀开发者代码 这块核心就是遍历 并进行加载到builder中
//        for (int id : mInjectElements.keySet()) {
//            VariableElement variableElement = mInjectElements.get(id);
//            String name = variableElement.getSimpleName().toString();
//            String type = variableElement.asType().toString();
//            //这里object如果不为空，则可以传入view等对象
//            builder.append("        host." + name).append(" = ");
//            builder.append("(" + type + ")object.findViewById(" + id + ");\n");
//        }
//        for (int id : mInjectMethods.keySet()) {
//            ExecutableElement executableElement = mInjectMethods.get(id);
//            VariableElement variableElement = mInjectElements.get(id);
//            String name = variableElement.getSimpleName().toString();
//            builder.append("        host." + name + ".setOnClickListener(new View.OnClickListener(){\n");
//            builder.append("            @Override\n");
//            builder.append("            public void onClick(View v) {\n");
//            builder.append("                host." + executableElement.getSimpleName().toString() + "(host." + name + ");\n");
//            builder.append("            }\n");
//            builder.append("        });\n");
//        }
//        builder.append("    }\n");
//    }

    //方式二 依赖api 'com.squareup:javapoet:1.9.0'库
    //todo 好处是采用JavaPoet来替我们生成代码： 不需要手动去拼写 这种方式更好
    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("//自动生成的注解类，勿动!!!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import com.xm.module_api.*;\n");
        builder.append("import android.support.annotation.Keep;\n");
        builder.append("import android.view.View;\n");
        builder.append("import " + typeElement.getQualifiedName() + ";\n");
        builder.append('\n');
        builder.append("@Keep").append("\n");//禁止混淆，否则反射的时候找不到该类
        builder.append("public class ").append(getClassName());
        builder.append(" {\n");
        generateMethod(builder);
        builder.append("}\n");
        return builder.toString();
    }
    private void generateMethod(StringBuilder builder) {
        builder.append("    public " + getClassName() + "(final " + typeElement.getSimpleName() + " host, View object) {\n");
        if (value > 0) {
            builder.append("        host.setContentView(" + value + ");\n");
        }
        for (int id : mInjectElements.keySet()) {
            VariableElement variableElement = mInjectElements.get(id);
            String name = variableElement.getSimpleName().toString();
            String type = variableElement.asType().toString();
            //这里object如果不为空，则可以传入view等对象
            builder.append("        host." + name).append(" = ");
            builder.append("(" + type + ")object.findViewById(" + id + ");\n");
        }
        for (int id : mInjectMethods.keySet()) {
            ExecutableElement executableElement = mInjectMethods.get(id);
            VariableElement variableElement = mInjectElements.get(id);
            String name = variableElement.getSimpleName().toString();
            builder.append("        host." + name + ".setOnClickListener(new View.OnClickListener(){\n");
            builder.append("            @Override\n");
            builder.append("            public void onClick(View v) {\n");
            builder.append("                host." + executableElement.getSimpleName().toString() + "(host." + name + ");\n");
            builder.append("            }\n");
            builder.append("        });\n");
        }
        builder.append("    }\n");
    }
}
