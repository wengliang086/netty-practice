package com.my.practice.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射文件，属于堆外内存
 */
public class NioTest9 {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("input.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        System.out.println(mappedByteBuffer.get(0));
        System.out.println(mappedByteBuffer.get(3));
        mappedByteBuffer.put(0, (byte) '0');
        mappedByteBuffer.put(3, (byte) '3');
        System.out.println(mappedByteBuffer.get(0));
        System.out.println(mappedByteBuffer.get(3));

        randomAccessFile.close();
    }
}
