package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jdragon.tljrobot.client.config.FinalConfig;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import com.jdragon.tljrobot.tljutils.HttpUtils;
import com.jdragon.tljrobot.tljutils.response.Result;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URLEncoder;

/**
 * Create by Jdragon on 2020.01.14
 */
public class LoginEvent {
    public static String start(String username, String password) {
        try {
//            username = URLEncoder.encode(username, FinalConfig.ENCODING);
            HttpUtils httpUtils = HttpUtils.initJson();
            httpUtils.setBody("username", username);
            httpUtils.setBody("password", password);
            httpUtils.setMethod(RequestMethod.POST);
            String s = httpUtils.checkExec(HttpAddr.LOGIN_ADDR);
            Result<String> result = JSONObject.parseObject(s, new TypeReference<Result<String>>() {
            });
//            JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(HttpAddr.LOGIN_ADDR, username, password));

            if (result.result()) {
                UserState.loginState = true;
                UserState.token = "Bearer " + result.getResult();
                TypeNumManagerThread.getInstance().setLocalNumFromServer();
                TypeNumManagerThread.getInstance().start();
                new KeepALiveThread().start();
            }
            return "登录成功";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}