package com.my.practice.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class MyHttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /*pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));*/

        /**
         * HttpServerCodec 是一个双工的 Handler，里面同时实现了 HttpRequestDecoder 和 HttpResponseEncoder
         */
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("myHttpServerHandler", new MyHttpServerHandler());
    }
}
