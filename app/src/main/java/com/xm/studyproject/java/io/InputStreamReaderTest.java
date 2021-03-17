package com.xm.studyproject.java.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * 输入流 将字节流转化成字符流
 */
public class InputStreamReaderTest {
    void testInputStreamReader(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            //字符字节转化流  编码格式
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            char[] chars = new char[1024];
            while (bufferedReader.read(chars) != -1) {
                System.out.println("字节流转化成字符流" + chars.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
