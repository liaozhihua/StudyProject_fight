package com.xm.studyproject.android.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * 这个是服务端的service
 */
public class AIDLService2 extends Service {

    private List<User> users;

    @Override
    public void onCreate() {
        super.onCreate();
        initUser();
    }

    private void initUser() {
        users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("用户" + i);
            user.setAge(10 + i);
            users.add(user);
        }
    }
    private UserController.Stub stub = new UserController.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                               double aDouble, String aString) throws RemoteException {

        }

        @Override
        public boolean addUser(User user) throws RemoteException {
            boolean add = users.add(user);
            return add;
        }

        @Override
        public List<User> getUsers() throws RemoteException {
            return users;
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
