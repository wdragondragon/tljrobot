package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.entry.History;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.threadEvent.SynchronousOperation;
import com.jdragon.tljrobot.tljutils.HttpUtil;

public class UploadMatchAch implements SynchronousOperation {
    private History history;
    public UploadMatchAch(History history){
        this.history = history;
    }
    @Override
    public void start() {
        JSON.parseObject(HttpUtil.doPostObject(HttpAddr.MATCH_UPLOAD_TLJ_MATCH_ACH,history, UserState.token));
    }
}
