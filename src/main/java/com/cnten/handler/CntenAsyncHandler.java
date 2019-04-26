package com.cnten.handler;

import com.cnten.resolve.AbstractResolve;
import io.vertx.core.Handler;
import io.vertx.core.datagram.DatagramPacket;
import io.vertx.core.datagram.DatagramSocket;

/**
 * Created by XHD on 2018/3/15.
 */
public class CntenAsyncHandler implements Handler<DatagramPacket> {
	private DatagramSocket socket = null;
	private AbstractResolve abstractResolve = null;
	public CntenAsyncHandler(DatagramSocket socket,AbstractResolve abstractResolve) {
		this.socket = socket;
		this.abstractResolve = abstractResolve;
	}

    @Override
    public void handle(DatagramPacket event) {
        String  data = event.data().getString(0, event.data().length());
//		String value = HexUtil.encodeHexStr(event.data().getBytes(), false);
//		System.out.println(data.length());
		abstractResolve.resolve(data,socket);
    }

}
