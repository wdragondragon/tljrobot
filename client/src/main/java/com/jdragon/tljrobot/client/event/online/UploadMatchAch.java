package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.entry.History;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.threadEvent.SynchronousOperation;
import com.jdragon.tljrobot.client.utils.common.HistoryUtil;
import com.jdragon.tljrobot.tljutils.HttpUtil;

public class UploadMatchAch implements SynchronousOperation {
    @Override
    public void start() {
        History history = HistoryUtil.getHistoryEntry();
        history.setParagraph(0);
        JSON.parseObject(HttpUtil.doPostObject(HttpAddr.MATCH_UPLOAD_TLJ_MATCH_ACH,history, UserState.token));
    }
}
