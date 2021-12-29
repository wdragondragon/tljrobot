package com.jdragon.tljrobot.client.event.online;

import com.jdragon.tljrobot.client.api.AccountApi;
import com.jdragon.tljrobot.client.entry.HistoryDto;
import com.jdragon.tljrobot.client.event.threadEvent.SynchronousOperation;
import com.jdragon.tljrobot.tljutils.zFeign.DynaProxyHttp;

public class UploadHistory implements SynchronousOperation {

    private final AccountApi accountApi = DynaProxyHttp.getInstance(AccountApi.class);

    HistoryDto historyDto;

    public UploadHistory(HistoryDto historyDto) {
        this.historyDto = historyDto;
    }

    @Override
    public void start() {
        accountApi.uploadHistoryAndArticle(historyDto);
    }
}
