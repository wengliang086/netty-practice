package com.my.practice.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest8 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output2.txt");

        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();

        /**
         * 这里才是重点 allocateDirect
         */
        ByteBuffer buffer = ByteBuffer.allocateDirect(128);
        while (true) {
            buffer.clear();// 注释掉这里会死循环，这里一定要深刻理解
            int read = inputChannel.read(buffer);
            System.out.println("read: " + read);
            if (read == -1) {
                break;
            }
            buffer.flip();
            outputChannel.write(buffer);
        }
        inputChannel.close();
        outputChannel.close();
    }
}
