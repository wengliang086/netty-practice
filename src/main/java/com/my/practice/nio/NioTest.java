package com.my.practice.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class NioTest {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(10);
//        for (int i = 0; i < intBuffer.capacity(); i++) {
        for (int i = 0; i < 5; i++) {
            int randomNumber = new SecureRandom().nextInt(20);
            intBuffer.put(randomNumber);
        }
        System.out.println("before flip limit: " + intBuffer.limit());
        System.out.println("before flip position: " + intBuffer.position());
        intBuffer.flip();
        System.out.println("after flip limit: " + intBuffer.limit());
        System.out.println("after flip position: " + intBuffer.position());
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
        System.out.println("after get limit: " + intBuffer.limit());
        System.out.println("after get position: " + intBuffer.position());
    }
}
