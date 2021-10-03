package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.entry.HistoryDto;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.threadEvent.SynchronousOperation;
import com.jdragon.tljrobot.tljutils.HttpUtils;
import com.jdragon.tljrobot.tljutils.response.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

public class UploadHistory implements SynchronousOperation {
    HistoryDto historyDto;
    public UploadHistory(HistoryDto historyDto){
        this.historyDto = historyDto;
    }
    @Override
    public void start() {
        HttpUtils httpUtils = HttpUtils.initJson();
        httpUtils.setMethod(RequestMethod.POST);
        httpUtils.setHeader(HttpHeaders.AUTHORIZATION, UserState.token);
        httpUtils.setBody(historyDto);
        String s = httpUtils.checkExec(HttpAddr.HISTORY_UPLOAD_HISTORY_ARTICLE);
        JSONObject.parseObject(s, new TypeReference<Result<String>>() {
        });

//        JSON.parseObject(HttpUtil.doPostObject(HttpAddr.HISTORY_UPLOAD_HISTORY_ARTICLE,historyDto, UserState.token));
    }
}
