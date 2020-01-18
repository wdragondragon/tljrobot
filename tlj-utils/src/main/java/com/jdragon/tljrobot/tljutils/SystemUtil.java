package com.jdragon.tljrobot.tljutils;

import java.util.Properties;
public class SystemUtil {
    public static String getSystemName(){
//        try {
            String systemName;
            String systemVersion;
//            String systemIp;
//            String hostName;
//            InetAddress addr;
//            addr = InetAddress.getLocalHost();
//            systemIp = addr.getHostAddress(); //获取本机ip
//            hostName = addr.getHostName(); //获取本机计算机名称
//            System.out.println("本机IP：" + systemIp + "\n本机名称:" + hostName);
            Properties props = System.getProperties();
            systemName = props.getProperty("os.name");
            systemVersion = props.getProperty("os.version");
            System.out.println("操作系统的名称：" + systemName);
            System.out.println("操作系统的版本：" + systemVersion);
            return systemName;
//        } catch (UnknownHostException e3) {
//            // TODO Auto-generated catch block
//            e3.printStackTrace();
//            return null;
//        }
    }
    public static boolean isWindows(){
        String systemName = getSystemName();
        if(systemName!=null&&systemName.length()>=7&&systemName.substring(0,7).equals("Windows")){
            System.out.println("win");
            return true;
        }

        else return false;
    }
}
