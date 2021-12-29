package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jdragon.tljrobot.client.api.AccountApi;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.*;
import com.jdragon.tljrobot.client.event.threadEvent.CountMatchThread;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import com.jdragon.tljrobot.tljutils.HttpUtils;
import com.jdragon.tljrobot.tljutils.response.Result;
import com.jdragon.tljrobot.tljutils.response.table.PageTable;
import com.jdragon.tljrobot.tljutils.zFeign.DynaProxyHttp;
import com.jdragon.tljrobot.tljutils.zFeign.HttpException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.swing.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by Jdragon on 2020.01.18
 */
public class HistoryEvent {

    private static final AccountApi accountApi = DynaProxyHttp.getInstance(AccountApi.class);

    public static void getMatch() {
        /**
         * 获取数据示范
         * {"code":200,"message":"获取成功","result":{"id":0,"holdDate":"2020-01-18","article":{"id":1,"title":"1","content":"1"}}}
         */
        TypingMatchVO typingMatchVO = accountApi.getTodayMatch(false);
        ArticleDto articleDto = typingMatchVO.getArticle();
        Article.getArticleSingleton(0, articleDto.getTitle(), articleDto.getContent());
        SwingSingleton.typingText().setEditable(false);
        new CountMatchThread().start();
    }

    public static HistoryList getHistoryByPage(int page) {
        PageTable<History> pageTable = accountApi.getMyTypeHistory(page, 10);
        HistoryList historyListEntry = new HistoryList();
        historyListEntry.setHistoryList(pageTable.getTable().getBodies());
        historyListEntry.setPageNum(Integer.parseInt(pageTable.getCurrent().toString()));
        historyListEntry.setPages(Integer.parseInt(pageTable.getPages().toString()));
        historyListEntry.setTotal(Integer.parseInt(pageTable.getTotal().toString()));
        historyListEntry.setHasNextPage(pageTable.getHasNextPage());
        historyListEntry.setHasPreviousPage(pageTable.getHasPreviousPage());
        return historyListEntry;
    }

    public static List<History2> getTljMatchAchList(Date date) {
        PageTable<History2> pageTable = accountApi.getPCTljMatchAchByDate(date, 1, false);
        return pageTable.getTable().getBodies();
    }
}
