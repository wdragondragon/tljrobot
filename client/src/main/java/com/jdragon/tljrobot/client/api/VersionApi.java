package com.jdragon.tljrobot.client.api;

import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.tljutils.zFeign.ZFeign;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author JDragon
 * @Date 2021.12.29 下午 10:21
 * @Email 1061917196@qq.com
 * @Des:
 */
@ZFeign(baseUrl = HttpAddr.SERVER_ADDR_NEW, basePath = "/version", depth = "result", fallback = VersionFallback.class)
public interface VersionApi {
    @GetMapping("/newVersion1")
    JSONObject getNewVersion();
}
