package com.xm.studyproject.java.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile 注意啊跟InputStream和OutputStream没有任何关系
 * 好处：可以指定文件位置读写 所以就是我们可以 分段下载。通过seek( )方法
 * 特点1：RandomAccessFile不属于InputStream和OutputStream类系的它是一个完全独立的类，所有方法(绝大多数都只属于它自己)都是自己从头开始规定的,
 * 特点2：这里面包含读写两种操作  可设置只读等等模式
 */
public class RandomAccessFileTest {

    private static final File file = new File("src\\testtxt\\raf.txt");

    void testRandomAccessFile(File file) {
        //file有可能是文件也有可能是目录
        if (file.exists()) {
            file.delete();
        }
        //参数 mode 的值可选 "r"：可读，"w" ：可写，"rw"：可读性
        try {
            RandomAccessFile rw = new RandomAccessFile(file, "rw");
            //seek(int index);可以将指针移动到某个位置开始读写;
            rw.seek(1000);
            //setLength(long len);给写入文件预留空间： 这块空间不可操作
            rw.setLength(1000);
            rw.read(new byte[100]);
            rw.writeChar('a');
            rw.writeChars("abcde");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
