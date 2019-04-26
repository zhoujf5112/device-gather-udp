package com.cnten.resolve;

import io.vertx.core.datagram.DatagramSocket;

public interface AbstractResolve {
     void resolve(String data, DatagramSocket socket);
}
