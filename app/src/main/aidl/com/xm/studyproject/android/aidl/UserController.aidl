// PersonController.aidl
package com.xm.studyproject.android.aidl;

// Declare any non-default types here with import statements
import com.xm.studyproject.android.aidl.User;
interface UserController {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
//            in：输入类型参数
//            out：输出类型参数
//            inout：输入输出类型参数
            boolean addUser(in User user);
            List<User> getUsers();
}