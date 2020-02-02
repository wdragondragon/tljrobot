package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.config.OnlineConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import lombok.SneakyThrows;

/**
 * Create by Jdragon on 2020.01.17
 */
public class LogoutEvent {
    @SneakyThrows
    public static boolean start(){
        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(OnlineConfig.LOGOUT_ADDR,UserState.token));
        if(jsonObject.getString(Constant.RESPONSE_MESSAGE).equals("退出成功")) {
            UserState.loginState = false;
            UserState.token = "";
            return true;
        }else{
            return false;
        }
    }
}