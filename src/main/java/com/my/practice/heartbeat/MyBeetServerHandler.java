package com.my.practice.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyBeetServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    System.out.println("READER_IDLE");
                    break;
                case WRITER_IDLE:
                    System.out.println("WRITER_IDLE");
                    break;
                case ALL_IDLE:
                    System.out.println("ALL_IDLE");
                    break;
            }
        }
    }
}
