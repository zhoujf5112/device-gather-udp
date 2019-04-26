package com.cnten.resolve.impl;

import com.cnten.resolve.AbstractResolve;
import com.cnten.sender.DeviceSender;
import io.vertx.core.datagram.DatagramSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: gaoTeng
 * @Date: 2018/9/4 0004
 * @Description:
 */
@Component
public class DeviceResolve implements AbstractResolve {

	private static final Logger log = LoggerFactory.getLogger(DeviceResolve.class);

	private StringBuffer dataBuffer = new StringBuffer();

	@Autowired
	private DeviceSender deviceSender;

    /**
     * 针对UDP分包接受到后组包
     * @param data
     * @param socket
     */
	@Override
	public void resolve(String data, DatagramSocket socket) {
        log.info("server receive data:"+data);
        String sData = replaceBlank(data);
        dataBuffer.append(sData);
        if (!dataBuffer.toString().startsWith("$")) {
            if (dataBuffer.toString().contains("$")){
                int index = dataBuffer.toString().indexOf("$");
                String str = dataBuffer.toString().substring(index,dataBuffer.toString().length());
                dataBuffer.setLength(0);
                dataBuffer.append(str);
            }
        }
        if (dataBuffer.toString().startsWith("$")) {
            int[] indexArr = getIndexofChar(dataBuffer.toString(), "$");
            if (indexArr.length >= 2){
                String msg =  dataBuffer.toString().substring(indexArr[0],indexArr[1]+1);
                String nextData = dataBuffer.toString().substring(indexArr[1]+1,dataBuffer.toString().length());
                dataBuffer.setLength(0);
                dataBuffer.append(nextData);
                log.info("server receive msg:"+msg);
                log.info("server receive dataBuffer:"+dataBuffer.toString());
                deviceSender.send(msg);
            }
        }
        socket.close();
	}


    /**
     * 获取某一个字符串中字符的位置
     * @param str
     * @param c
     * @return
     */
	private static int[] getIndexofChar(String str,String c){
        int index = 0;
	    int[] ary = {};
	    while((index = str.indexOf(c)) != -1){
            ary= Arrays.copyOf(ary, ary.length+1);
            ary[ary.length-1] = index;
            str = str.substring(index + c.length());
        }
	    return ary;
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n|\r\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
