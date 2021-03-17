package com.xm.studyproject.java.io;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 字节流例子 数据流
 */
public class DataStreamTest {
    private static final String TAG = "DataStreamTest";

    //测试输入流 （就是将内存/磁盘中 读到输入流中 ）
    void testDataInputStream(File file) {
        try {
            //FileInputStream extends InputStream
            //InputStream 并没有实现read方法 是暴漏出去了
            FileInputStream fileInputStream = new FileInputStream(file);
            //缓冲流 避免按字节一个个读到内存/磁盘中 DEFAULT_BUFFER_SIZE = 8192
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            //数据的包装类 可以read各种基本类型的数据
            DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
            //读的时候需要跟写顺序保持一致  不然会出现错乱
            dataInputStream.readBoolean();
            dataInputStream.readBoolean();
            dataInputStream.readDouble();
            //字节地方不推荐使用读行
            //dataInputStream.readLine()
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //测试输出流 （就是将流内容输出到磁盘上 ）
    void testDataOutputStream(File file) {
        try {
            //同上 OutputStream没有实现write方法
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
            dataOutputStream.writeBoolean(true);
            dataOutputStream.write(26);
            dataOutputStream.writeChars("测试数据");
            dataOutputStream.writeDouble(23.8);
            Log.i(TAG, "testDataOutputStream: "+dataOutputStream.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
