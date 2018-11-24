package com.my.practice.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * FileLock
 */
public class NioTest10 {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("input.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        FileLock fileLock = fileChannel.lock(3, 6, true);
        System.out.println("Valid: " + fileLock.isValid());
        System.out.println("Lock type: " + fileLock.isShared());
        fileLock.release();

        randomAccessFile.close();
    }
}
