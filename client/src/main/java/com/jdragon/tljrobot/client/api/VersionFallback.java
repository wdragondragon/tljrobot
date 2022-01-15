package com.jdragon.tljrobot.client.api;

import com.alibaba.fastjson.JSONObject;

/**
 * <p></p>
 * <p>create time: 2022/1/5 0:18 </p>
 *
 * @author : Jdragon
 */
public class VersionFallback implements VersionApi{
    @Override
    public JSONObject getNewVersion() {
        System.out.println("获取新版本失败");
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("appVersion","获取个毛线");
//        return jsonObject;
        return null;
    }
}
