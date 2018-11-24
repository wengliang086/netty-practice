package com.my.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 关于 Buffer 的 Scattering 与 Gathering
 *
 * 注意：这里不需要用到 Selector，需要非常明确 Selector的意义 TODO
 */
public class NioTest11 {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8899);
        serverSocketChannel.bind(inetSocketAddress);
//        serverSocketChannel.socket().bind(inetSocketAddress);
        int messageLength = 2 + 3 + 4;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();
        while (true) {
            int bytesRead = 0;
            while (bytesRead < messageLength) {
                long r = socketChannel.read(buffers);
                bytesRead += r;
                System.out.println("bytesRead: " + bytesRead);

                /**
                 * buffers 转 buffer 转 string 再输出
                 */
                Arrays.asList(buffers).stream()
                        .map(buffer -> "position: " + buffer.position() + ", limit: " + buffer.limit())
                        .forEach(System.out::println);
//                Arrays.stream(buffers)
//                        .map(buffer -> "position: " + buffer.position() + ", limit: " + buffer.limit())
//                        .forEach(System.out::println);
            }

//            Arrays.asList(buffers).forEach(buffer->{
//                buffer.flip();
//            });
            Arrays.asList(buffers).forEach(Buffer::flip);

            int bytesWritten = 0;
            while (bytesWritten < messageLength) {
                long r = socketChannel.write(buffers);
                bytesWritten += r;
            }
            Arrays.asList(buffers).forEach(Buffer::clear);
            System.out.println("bytesRead: " + bytesRead + ", bytesWrite: " + bytesWritten + ", messageLength: " + messageLength);
        }

    }
}
