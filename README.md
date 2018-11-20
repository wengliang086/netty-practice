## netty 深度实践

* chat
    1. DelimiterBasedFrameDecoder
    2. StringEncoder
    3. StringDecoder
* heartbeat
    1. IdleStateHandler
* http
    1. HttpRequestDecoder
    2. HttpResponseEncoder
    3. HttpObjectAggregator
    4. 或者 HttpServerCodec
* socket
* websocket
    1. HttpServerCodec
    2. ChunkedWriteHandler
    3. HttpObjectAggregator
    4. WebSocketServerProtocolHandler
