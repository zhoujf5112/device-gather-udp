package com.cnten;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TcpClientTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		// 3次TCP连接，每个连接发送2个请求数据
		for (int i = 0; i < 1; i++) {
			Socket socket = null;
			OutputStream out = null;
			InputStream in = null;
			try {
				socket = new Socket("localhost", 20001);
				out = socket.getOutputStream();
				// 第一次请求服务器
				String lines1 = "7B010016313338303030303130303764423EB6A6237B";

				
				byte[] outputBytes1 = lines1.getBytes("UTF-8");
				out.write(outputBytes1);
				out.flush();
				in = socket.getInputStream();
				byte[] data = new byte[32];
				int index = in.read(data,0,32);
				System.out.println(index);
				System.out.println(new String(data,"UTF-8"));
				// 第二次请求服务器
				// String lines2 = "World\r\n";
				// byte[] outputBytes2 = lines2.getBytes("UTF-8");
				// out.write(outputBytes2);
				// out.flush();
			} finally {
				// 关闭连接
				if (out != null)
					out.close();
				if(in!=null)
					in.close();
				if (socket != null)
					socket.close();
			}
		}
	}

}
