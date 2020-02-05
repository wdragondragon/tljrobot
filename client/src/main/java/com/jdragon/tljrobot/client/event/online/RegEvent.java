package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.config.FinalConfig;
import com.jdragon.tljrobot.client.config.OnlineConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import lombok.SneakyThrows;

import java.net.URLEncoder;

/**
 * Create by Jdragon on 2020.02.02
 */
public class RegEvent {
    @SneakyThrows
    public static String start(String username, String password) {
        username = URLEncoder.encode(username, FinalConfig.ENCODING);
        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(OnlineConfig.REG_ADDR,username,password));
        return jsonObject.getString(Constant.RESPONSE_MESSAGE);
    }
}