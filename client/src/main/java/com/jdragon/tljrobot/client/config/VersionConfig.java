package com.jdragon.tljrobot.client.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.ArticleDto;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import com.jdragon.tljrobot.tljutils.HttpUtils;
import com.jdragon.tljrobot.tljutils.response.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import static javax.swing.UIManager.getString;

/**
 * Create by Jdragon on 2020.02.02
 */
public class VersionConfig {
    public static String start() {
        try {

            HttpUtils httpUtils = HttpUtils.initJson();
            httpUtils.setMethod(RequestMethod.GET);
            String s = httpUtils.checkExec(HttpAddr.GET_TLJ_NEW_VERSION);

            Result<JSONObject> result = JSONObject.parseObject(s, new TypeReference<Result<JSONObject>>() {
            });

            if (result.result()) {
                JSONObject jsonObject = result.getResult();
                return jsonObject.getString("appVersion");
            }
        } catch (Exception e) {

        }
        return "获取失败";
    }
}
