package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.config.FinalConfig;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.tljutils.HttpUtil;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Create by Jdragon on 2020.01.14
 */
public class LoginEvent {
    public static String start(String username, String password) {
        try {
            username = URLEncoder.encode(username, FinalConfig.ENCODING);
            JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(HttpAddr.LOGIN_ADDR, username, password));

            if ("登录成功".equals(jsonObject.getString(Constant.RESPONSE_MESSAGE))) {
                UserState.loginState = true;
                UserState.token = jsonObject.getString(Constant.RESPONSE_RESULT);
                UserState.loginUserName = URLDecoder.decode(username, FinalConfig.ENCODING);
                TypeNumManagerThread.getInstance().setLocalNumFromServer();
                TypeNumManagerThread.getInstance().start();
                new KeepALiveThread().start();
            }
            return jsonObject.getString(Constant.RESPONSE_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            return "登录失败";
        }
    }
}