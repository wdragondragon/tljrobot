package com.jdragon.tljrobot.client.entry;

import com.alibaba.nacos.common.util.Md5Utils;
import com.jdragon.tljrobot.client.config.FinalConfig;

/**
 * Create by Jdragon on 2020.01.17
 */
public class NumState {
    public static int num;
    public static int rightNum;
    public static int misNum;
    public static int dateNum;
    public static String getNumKey(){
        return Md5Utils.getMD5((num+rightNum+misNum+ dateNum+ FinalConfig.SECRET).getBytes());
    }
}
