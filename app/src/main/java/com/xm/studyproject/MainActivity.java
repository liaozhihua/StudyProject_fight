package com.xm.studyproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xm.studyproject.java.annotation.BindApi;
import com.xm.studyproject.java.annotation.BindClick;
import com.xm.studyproject.java.annotation.BindId;
import com.xm.studyproject.java.thread.threadpool.ThreadPoolDemo;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;

@BindId(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {


    @BindId(R.id.tv_annotation)
    TextView tvAnnotation;

    @BindId(R.id.tv_annotation_click)
    TextView tvAnnotationClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //设置
        BindApi.bind(this);
        tvAnnotation.setText  ("运行期通过注解来设置成员变量");
        BindApi.click(this);
//        ReflectDemo.testClass("com.xm.studyproject.java.reflect.Person");
//        ReflectDemo.testConstructor("com.xm.studyproject.java.reflect.Person");
//        ReflectDemo.testMethod(Person.class);
//        ReflectDemo.testField(Person.class);
//        Intent intent = new Intent(this, processorActivity.class);
//        startActivity(intent);
        //静态代理实例
//        PublicInterface target1 = new Target1();
//        StaticProxyDemo staticProxyDemo = new StaticProxyDemo(target1);
//        staticProxyDemo.buyFood1();
//        staticProxyDemo.buyFood2();
//        DynamicProxyDemo dynamicProxyDemo = new DynamicProxyDemo();
        //获取动态代理类的实例
//        PublicInterface proxy1 = (PublicInterface) dynamicProxyDemo.getProxy(new DynamicTarget1());
//        String food1 = proxy1.buyFood1();
//        PublicInterface2 proxy2 = (PublicInterface2) dynamicProxyDemo.getProxy(new DynamicTarget2());
//        proxy2.buyFruit1();
//        SingleThreadDemo threadDemo = new SingleThreadDemo();
//        threadDemo.createThread();
//        SyncDemo syncDemo = new SyncDemo();
//        syncDemo.countAddSyn();
//        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
//        threadLocalDemo.test();
//        LockDemo lockDemo = new LockDemo();
//        lockDemo.test();
//        lockDemo.test2();
        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
        threadPoolDemo.getThreadPool();
        threadPoolDemo.test();
    }


    @BindClick({R.id.tv_annotation,R.id.tv_annotation_click})
    @Keep
    private void onClick(View viewId) {
        switch (viewId.getId()) {
            case R.id.tv_annotation:
                tvAnnotation.setText("点击事件1");
                break;
            case R.id.tv_annotation_click:
                tvAnnotationClick.setText("点击事件2");
                break;
        }
    }
}
