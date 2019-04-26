package com.cnten.utils;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

public class SocketUtil {
    public static void replyData(String data, NetSocket socket){
        socket.write(Buffer.buffer(StringUtils.toBytes(data)));
    }
}
