package com.jdragon.tljrobot.client.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jdragon.tljrobot.client.api.VersionApi;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.ArticleDto;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import com.jdragon.tljrobot.tljutils.HttpUtils;
import com.jdragon.tljrobot.tljutils.response.Result;
import com.jdragon.tljrobot.tljutils.zFeign.DynaProxyHttp;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import static javax.swing.UIManager.getString;

/**
 * Create by Jdragon on 2020.02.02
 */
public class VersionConfig {
    private final static VersionApi versionApi = DynaProxyHttp.getInstance(VersionApi.class);

    public static String start() {
        JSONObject jsonObject = versionApi.getNewVersion();
        return jsonObject.getString("appVersion");
    }
}
