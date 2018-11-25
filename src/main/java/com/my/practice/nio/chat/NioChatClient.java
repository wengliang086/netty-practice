package com.my.practice.nio.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioChatClient {

    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("localhost", 8899));

        while (true) {
            int num = selector.select();
            Set<SelectionKey> keySet = selector.selectedKeys();
            for (SelectionKey selectionKey : keySet) {
                if (selectionKey.isConnectable()) {
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    if (client.isConnectionPending()) {
                        client.finishConnect();

                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        buffer.put((LocalDateTime.now() + " 连接成功").getBytes());

                        buffer.flip();
                        client.write(buffer);

                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                while (true) {
                                    try {
                                        buffer.clear();
                                        InputStreamReader reader = new InputStreamReader(System.in);
                                        BufferedReader bufferedReader = new BufferedReader(reader);
                                        String msg = bufferedReader.readLine();
                                        buffer.put(msg.getBytes());
                                        buffer.flip();
                                        client.write(buffer);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                    client.register(selector, SelectionKey.OP_READ);
                }
                if (selectionKey.isReadable()) {
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int read = client.read(buffer);
                    if (read > 0) {
                        String receivedMessage = new String(buffer.array(), 0, read);
                        System.out.println(receivedMessage);
                    }
                }
            }
            keySet.clear();
        }
    }
}
