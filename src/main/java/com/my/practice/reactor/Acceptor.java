package com.my.practice.reactor;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public Acceptor(ServerSocketChannel serverSocketChannel, Selector selector) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            SocketChannel client = serverSocketChannel.accept();
            if (client != null) {
                new Handler(selector, client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
