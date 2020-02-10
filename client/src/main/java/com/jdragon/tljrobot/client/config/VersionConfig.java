package com.jdragon.tljrobot.client.config;

import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.tljutils.HttpUtil;

/**
 * Create by Jdragon on 2020.02.02
 */
public class VersionConfig {
    public static String start(){
        try {
            JSONObject jsonObject = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.GET_TLJ_NEW_VERSION));
            String retMessage = jsonObject.getString("message");
            if (retMessage.equals("获取成功")) {
                return jsonObject.getJSONObject("result").getString("version");
            }
        }catch (Exception e){}
        return "获取失败";
    }
}
