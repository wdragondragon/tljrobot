package com.jdragon.tljrobot.tljutils;

import lombok.Data;

import java.util.UUID;

@Data
public class Local {
    private static TimingMap<String,Object> loginMap = new TimingMap<>();
    public static TimingMap<String, Object> getTokenMap(){
        return loginMap;
    }
    public static boolean isLogin(String userid){
        if(loginMap.containsKey(userid)&&loginMap.get(userid)!=null)
            return true;
        else {
            return false;
        }
    }
    public static String login(Object object){
        String userid = UUID.randomUUID().toString();
        System.out.println(userid);
        loginMap.put(userid,object);
        return userid;
    }
    public static void logout(String userid){
        loginMap.remove(userid);
    }
    public static void login(String token,Object object){
        loginMap.put(token,object);
    }
    public static Object getSession(String  userId) {
        return loginMap.get(userId);
    }
    public static int size(){
        return loginMap.size();
    }
}
