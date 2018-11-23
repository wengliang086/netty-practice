## netty 深度实践

### nio

1. selector（Thread）

2. channel（类似IO中的stream）

3. buffer（一块内存数组）

### chat

1. DelimiterBasedFrameDecoder
2. StringEncoder
3. StringDecoder

### heartbeat

1. IdleStateHandler

### http

1. HttpRequestDecoder
2. HttpResponseEncoder
3. HttpObjectAggregator
4. 或者 HttpServerCodec

### socket

### webSocket

1. HttpServerCodec
2. ChunkedWriteHandler
3. HttpObjectAggregator
4. WebSocketServerProtocolHandler
