package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.tljutils.HttpUtil;

/**
 * Create by Jdragon on 2020.01.17
 */
public class LogoutEvent {
    public static boolean start(){
        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(HttpAddr.LOGOUT_ADDR,UserState.token));
        if("退出成功".equals(jsonObject.getString(Constant.RESPONSE_MESSAGE))) {
            UserState.loginState = false;
            UserState.token = "";
            TypeNumManagerThread.getInstance().stop();
            return true;
        }else{
            return false;
        }
    }
}