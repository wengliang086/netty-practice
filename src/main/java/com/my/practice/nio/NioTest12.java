package com.my.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * Selector、SelectionKey
 */
public class NioTest12 {

    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];
        ports[0] = 5001;
        ports[1] = 5002;
        ports[2] = 5003;
        ports[3] = 5004;
        ports[4] = 5005;

        // 一个 Selector 维护着三种 SelectionKey 的集合
        System.out.println(SelectorProvider.provider().getClass());
        System.out.println(sun.nio.ch.DefaultSelectorProvider.create().getClass());

        Selector selector = Selector.open();
        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//            System.out.println(serverSocketChannel.getClass());
            /**
             * 下面两种绑定方式应该是一样的
             */
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(ports[i]));
//            serverSocketChannel.bind(new InetSocketAddress(ports[i]));
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口：" + ports[i]);
        }

        while (true) {
            int num = selector.select();
            if (num <= 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);
                    iterator.remove();
                    System.out.println("获取客户端连接: " + socketChannel);
                }
                if (selectionKey.isReadable()) {
                    iterator.remove();
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int bytesRead = 0;
                    while (true) {
                        ByteBuffer buffer = ByteBuffer.allocate(512);
                        int read = socketChannel.read(buffer);
                        if (read <= 0) {
                            break;
                        }
                        bytesRead += read;
                        buffer.flip();
                        socketChannel.write(buffer);
                    }
                    System.out.println("bytesRead: " + bytesRead);
                }
            }
        }
    }
}
