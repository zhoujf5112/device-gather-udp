package com.cnten.utils;

import io.vertx.core.net.NetSocket;

import java.util.HashMap;
import java.util.Map;

public class SocketMap {

    private static Map<String, NetSocket> map = new HashMap<>();

    private SocketMap() {
    }
    //添加socket
    public static void addSocket(String id,NetSocket socket){
        if (!map.containsKey(id)) {
            //不存在直接添加
            map.put(id, socket);
        }else {
            //存在
            NetSocket socket1 = getSocket(id);
            //当前socket和已保存的是否一致，不一致清除添加新的，一致则不变
            if (socket1 != socket) {
                removeSocket(id);
                map.put(id, socket);
            }
        }
    }
    //根据id获取socket
    public static NetSocket getSocket(String id){
        NetSocket netSocket = map.get(id);
        if (netSocket != null) {
            return netSocket;
        }else {
            throw new RuntimeException("设备id是"+id+"的设备未上线！");
        }
    }
    //根据id删除socket
    public static void removeSocket(String id){
        map.remove(id);
    }

}
