package com.jdragon.tljrobot.tljutils;

import lombok.Data;


import java.util.UUID;

@Data
public class Local {
    private static TimingMap loginMap = new TimingMap();

    public static boolean isLogin(String userid){
        if(loginMap.containsKey(userid))
            return true;
        else return false;
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

    public static Object getSession(String  userId) {
        return loginMap.get(userId);
    }
}
