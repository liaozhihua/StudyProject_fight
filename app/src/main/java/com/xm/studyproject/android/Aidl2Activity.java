package com.xm.studyproject.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import java.util.List;

import com.xm.studyproject.R;
import com.xm.studyproject.android.aidl.Book;
import com.xm.studyproject.android.aidl.User;
import com.xm.studyproject.android.aidl.UserController;

public class Aidl2Activity extends AppCompatActivity {

    private static final String TAG = "Aidl2Activity";
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl2);
        findViewById(R.id.btn_getUserList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connect) {
                    try {
                        List<User> users = userController.getUsers();
                        log(users);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        findViewById(R.id.btn_addUser_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connect) {
                    User user = new User();
                    user.setAge(100);
                    user.setName("新增用户");
                    try {
                        userController.addUser(user);
                        Log.i(TAG, "新用户名：" + user.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Intent intent = new Intent();
        //如果不设置包名 Caused by: java.lang.IllegalArgumentException: Service Intent must be explicit:
        // { act=com.xm.studyproject.user }
        intent.setPackage("com.xm.studyproject");
        intent.setAction("com.xm.studyproject.user");
        //启动service 2种方式参考https://blog.csdn.net/imxiangzi/article/details/76039978
        //service使用注意细则也参考上面那篇文章 源码分析也可参考
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
    private boolean connect = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            connect = true;
            userController = UserController.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connect = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //service释放
        unbindService(connection);
    }

    private void log(List<User> users) {
        for (User user : users) {
            Log.i(TAG, "名字："+user.getName()+"年龄"+user.getAge());
        }
    }
}