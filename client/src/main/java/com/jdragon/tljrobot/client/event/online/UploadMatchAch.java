package com.jdragon.tljrobot.client.event.online;

import com.jdragon.tljrobot.client.api.AccountApi;
import com.jdragon.tljrobot.client.entry.History;
import com.jdragon.tljrobot.client.event.threadEvent.SynchronousOperation;
import com.jdragon.tljrobot.tljutils.zFeign.DynaProxyHttp;

public class UploadMatchAch implements SynchronousOperation {

    private final AccountApi accountApi = DynaProxyHttp.getInstance(AccountApi.class);

    private final History history;
    public UploadMatchAch(History history){
        this.history = history;
    }
    @Override
    public void start() {
        accountApi.uploadTljMatchAch(history);
    }
}
