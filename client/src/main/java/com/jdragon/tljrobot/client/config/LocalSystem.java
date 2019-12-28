package com.jdragon.tljrobot.client.config;

import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
@Data
public class LocalSystem {
    private static  LocalSystem localSystem = new LocalSystem();
    public static LocalSystem getInstance(){
        return localSystem;
    }
    private String systemName;
    private String systemVersion;
    private String systemIp;
    private String hostName;
    private LocalSystem(){
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            systemIp = addr.getHostAddress(); //获取本机ip
            hostName = addr.getHostName(); //获取本机计算机名称
            System.out.println("本机IP：" + systemIp + "\n本机名称:" + hostName);
            Properties props = System.getProperties();
            systemName = props.getProperty("os.name");
            systemVersion = props.getProperty("os.version");
            System.out.println("操作系统的名称：" + systemName);
            System.out.println("操作系统的版本：" + systemVersion);
        } catch (UnknownHostException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }
    }
    public static boolean isWindows(){
        if(LocalSystem.getInstance().getSystemName().substring(0,7).equals("Windows"))
            return true;
        else return false;
    }
}
