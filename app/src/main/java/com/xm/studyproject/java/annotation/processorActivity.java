package com.xm.studyproject.java.annotation;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xm.module_annotation.BindViewClick;
import com.xm.module_annotation.BindViewId;
import com.xm.module_api.HelperApi;
import com.xm.studyproject.R;

import androidx.appcompat.app.AppCompatActivity;

//在编译时期注解的使用
//因为这块主要针对的是编译时期的调试 就不能参考我们正常情况下调试的方式
//参考 https://blog.csdn.net/zhangle1hao/article/details/78637420
@BindViewId(R.layout.activity_processor)
public class processorActivity extends AppCompatActivity {

    @BindViewId(R.id.tv_processor)
    TextView tvProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView();
        HelperApi.inject(this);
        tvProcessor.setText("编译时期注解");
    }

    @BindViewClick(R.id.tv_processor)
    public void clickTest(View view) {
        switch (view.getId()) {
            case R.id.tv_processor:
                tvProcessor.setText("编译时期注解生效");
                break;
        }
    }
}
