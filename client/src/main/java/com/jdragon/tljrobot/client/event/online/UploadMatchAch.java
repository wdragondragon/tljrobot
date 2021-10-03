package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.entry.History;
import com.jdragon.tljrobot.client.entry.TypingMatchVO;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.threadEvent.SynchronousOperation;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import com.jdragon.tljrobot.tljutils.HttpUtils;
import com.jdragon.tljrobot.tljutils.response.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

public class UploadMatchAch implements SynchronousOperation {
    private History history;
    public UploadMatchAch(History history){
        this.history = history;
    }
    @Override
    public void start() {
        HttpUtils httpUtils = HttpUtils.initJson();
        httpUtils.setMethod(RequestMethod.POST);
        httpUtils.setHeader(HttpHeaders.AUTHORIZATION, UserState.token);
        httpUtils.setBody(history);
        String s = httpUtils.checkExec(HttpAddr.MATCH_UPLOAD_TLJ_MATCH_ACH);
        Result<String> result = JSONObject.parseObject(s, new TypeReference<Result<String>>() {
        });
//        JSON.parseObject(HttpUtil.doPostObject(HttpAddr.MATCH_UPLOAD_TLJ_MATCH_ACH,history, UserState.token));
    }
}
