package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.entry.HistoryDto;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.threadEvent.SynchronousOperation;
import com.jdragon.tljrobot.tljutils.HttpUtil;

public class UploadHistory implements SynchronousOperation {
    HistoryDto historyDto;
    public UploadHistory(HistoryDto historyDto){
        this.historyDto = historyDto;
    }
    @Override
    public void start() {
        JSON.parseObject(HttpUtil.doPostObject(HttpAddr.HISTORY_UPLOAD_HISTORY_ARTICLE,historyDto, UserState.token));
    }
}
