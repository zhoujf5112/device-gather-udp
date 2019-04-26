/*
 * Copyright 2017 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.cnten.vertx;

import com.cnten.handler.CntenAsyncHandler;
import com.cnten.resolve.impl.DeviceResolve;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class SpringVerticleManager extends AbstractVerticle {
	@Value("${vertx.http.port}")
	private int httpPort;
	@Value("${vertx.http.address}")
	private String address;

	@Autowired
	private DeviceResolve datangResolve;
	@Override
	public void start(Future<Void> startFuture) throws Exception {
        DatagramSocket socket = vertx.createDatagramSocket(new DatagramSocketOptions());
        socket.handler(new CntenAsyncHandler(socket ,datangResolve));
        socket.listen(httpPort, address, asyncResult -> {
            if(asyncResult.succeeded()) {
                System.out.println("socket Server is now listening on actual port: " + httpPort);
            } else{
                System.out.println("socket Failed to bind!");
            }
        });
	}

}