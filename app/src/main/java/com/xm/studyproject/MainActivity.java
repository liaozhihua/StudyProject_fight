package com.xm.studyproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xm.studyproject.annotation.BindApi;
import com.xm.studyproject.annotation.BindClick;
import com.xm.studyproject.annotation.BindId;
import com.xm.studyproject.reflect.Person;
import com.xm.studyproject.reflect.ReflectDemo;

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
        ReflectDemo.testClass("com.xm.studyproject.reflect.Person");
        ReflectDemo.testConstructor("com.xm.studyproject.reflect.Person");
        ReflectDemo.testMethod(Person.class);
        ReflectDemo.testField(Person.class);
//        Intent intent = new Intent(this, processorActivity.class);
//        startActivity(intent);
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
