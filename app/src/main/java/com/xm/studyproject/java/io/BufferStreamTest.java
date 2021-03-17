package com.xm.studyproject.java.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 字节流的 缓冲流实例
 */
public class BufferStreamTest {

    private static final byte[] byteArray = {
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
            0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A
    };

    //从流到磁盘中
    void testBufferOutStream(File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            bos.write(byteArray[0]);
            bos.write(byteArray, 1, byteArray.length - 1);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void bufferedInputStream(File file) {
        try {
            BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
            for(int i = 0; i < 10; i++) {
                //bin.available() 还有数据情况下
                if (bin.available() >= 0) {
                }
            }

            //
            bin.mark(6666);
            bin.skip(10);

            byte[] b = new byte[1024];
            int n1 = bin.read(b, 0, b.length);
            System.out.println("ʣ�����Ч�ֽ��� �� " + n1);

            //回到mark位置
            bin.reset();
            int n2 = bin.read(b,0, b.length);
            System.out.println("ʣ�����Ч�ֽ��� �� " + n2);

        }catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
