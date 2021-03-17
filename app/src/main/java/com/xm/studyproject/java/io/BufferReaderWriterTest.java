package com.xm.studyproject.java.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 字符输入流
 */
public class BufferReaderWriterTest {

    void testBufferReader(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //单行字符串值默认是80
            //bufferedReader.readLine()
            char[] chars = new char[1024];
            //一次性读1024个字节 如果内容为空 返回值为-1
            while (bufferedReader.read(chars) != -1) {
                System.out.println("字符串"+chars.toString());
            }
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void testBufferWriter(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("字符串");
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
