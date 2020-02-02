package com.jdragon.tljrobot.client.event.online;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.config.FinalConfig;
import com.jdragon.tljrobot.client.config.OnlineConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import lombok.SneakyThrows;

import java.net.URLEncoder;

/**
 * Create by Jdragon on 2020.01.14
 */
public class LoginEvent {
    @SneakyThrows
    public static boolean start(String username, String password) {
        username = URLEncoder.encode(username, FinalConfig.ENCODING);
        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(OnlineConfig.LOGIN_ADDR,username,password));
        if(jsonObject.getString(Constant.RESPONSE_MESSAGE).equals("登录成功")){
            UserState.loginState = true;
            UserState.token = jsonObject.getString(Constant.RESPONSE_RESULT);
            TypeNumManager.getInstance().setLocalNumFromServer();
            TypeNumManager.getInstance().start();
            new KeepALiveThread().start();
            return true;
        }else{
            return false;
        }
    }
}