package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.entry.*;
import com.jdragon.tljrobot.client.event.threadEvent.SynchronousOperation;
import com.jdragon.tljrobot.client.utils.common.HistoryUtil;
import com.jdragon.tljrobot.tljutils.HttpUtil;

public class UploadHistory implements SynchronousOperation {
    @Override
    public void start() {
        History history = HistoryUtil.getHistoryEntry();
        ArticleDto articleDto = new ArticleDto(0, Article.getArticleSingleton().getTitle(),Article.getArticleSingleton().getArticle());
        HistoryDto historyDto = new HistoryDto(articleDto,history);
        JSON.parseObject(HttpUtil.doPostObject(HttpAddr.HISTORY_UPLOAD_HISTORY_ARTICLE,historyDto, UserState.token));
    }
}
