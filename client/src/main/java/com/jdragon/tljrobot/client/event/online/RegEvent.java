package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.tljutils.HttpUtils;
import com.jdragon.tljrobot.tljutils.response.Result;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Create by Jdragon on 2020.02.02
 */
public class RegEvent {
    @SneakyThrows
    public static String start(String username, String password) {
        HttpUtils httpUtils = HttpUtils.initJson();
        httpUtils.setBody("username", username);
        httpUtils.setBody("password", password);
        httpUtils.setMethod(RequestMethod.POST);
        String s = httpUtils.checkExec(HttpAddr.REG_ADDR);
        Result<String> result = JSONObject.parseObject(s, new TypeReference<Result<String>>() {
        });
        return result.getMessage();
    }
}