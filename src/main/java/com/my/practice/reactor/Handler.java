package com.my.practice.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Handler implements Runnable {

    private static final int MAXIN = 1024;
    private static final int MAXOUT = 1024;
    final SocketChannel socket;
    final SelectionKey sk;
    ByteBuffer inBuffer = ByteBuffer.allocate(MAXIN);
    ByteBuffer outBuffer = ByteBuffer.allocate(MAXOUT);
    static final int READING = 0, SENDING = 1;
    int state = READING;

    Handler(Selector selector, SocketChannel socket) throws IOException {
        this.socket = socket;
        this.socket.configureBlocking(false);
        this.sk = socket.register(selector, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    boolean inputIsComplete() {
        return true;
    }

    boolean outputIsComplete() {
        return true;
    }

    void process() {
    }

    @Override
    public void run() {
        try {
            if (state == READING) read();
            if (state == SENDING) send();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void read() throws IOException {
        socket.read(inBuffer);
        if (inputIsComplete()) {
            process();
            state = SENDING;

            sk.interestOps(SelectionKey.OP_WRITE);
        }
    }

    void send() throws IOException {
        socket.write(outBuffer);
        if (outputIsComplete()) sk.channel();
    }
}
