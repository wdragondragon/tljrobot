package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.*;
import com.jdragon.tljrobot.client.event.threadEvent.CountMatchThread;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import com.jdragon.tljrobot.tljutils.HttpUtils;
import com.jdragon.tljrobot.tljutils.response.Result;
import com.jdragon.tljrobot.tljutils.response.table.PageTable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by Jdragon on 2020.01.18
 */
public class HistoryEvent {
    public static void getMatch() {
        HttpUtils httpUtils = HttpUtils.initJson();
        httpUtils.setMethod(RequestMethod.GET);
        httpUtils.setHeader(HttpHeaders.AUTHORIZATION, UserState.token);
        httpUtils.setParam("mobile", "0");
        String s = httpUtils.checkExec(HttpAddr.MATCH_GET_TODAY);
        Result<TypingMatchVO> result = JSONObject.parseObject(s, new TypeReference<Result<TypingMatchVO>>() {
        });
        if (result.result()) {
            /**
             * 获取数据示范
             * {"code":200,"message":"获取成功","result":{"id":0,"holdDate":"2020-01-18","article":{"id":1,"title":"1","content":"1"}}}
             */
            TypingMatchVO typingMatchVO = result.getResult();
            ArticleDto articleDto = typingMatchVO.getArticle();
            Article.getArticleSingleton(0, articleDto.getTitle(), articleDto.getContent());
            SwingSingleton.typingText().setEditable(false);
            new CountMatchThread().start();
        } else {
            JOptionPane.showMessageDialog(null, result.getMessage());
        }
    }

    public static HistoryList getHistoryByPage(int page) {
        HistoryList historyListEntry = new HistoryList();

        HttpUtils httpUtils = HttpUtils.initJson();
        httpUtils.setHeader(HttpHeaders.AUTHORIZATION, UserState.token);
        httpUtils.setHeader(Constant.PAGE_NUM, String.valueOf(page));
        httpUtils.setHeader(Constant.PAGE_SIZE, "10");
        httpUtils.setMethod(RequestMethod.GET);
        String s = httpUtils.checkExec(HttpAddr.ME_HISTORY_ADDR);
        Result<PageTable<History>> result = JSONObject.parseObject(s, new TypeReference<Result<PageTable<History>>>() {
        });
        PageTable<History> pageTable = result.getResult();
        historyListEntry.setHistoryList(pageTable.getTable().getBodies());
        historyListEntry.setPageNum(Integer.parseInt(pageTable.getCurrent().toString()));
        historyListEntry.setPages(Integer.parseInt(pageTable.getPages().toString()));
        historyListEntry.setTotal(Integer.parseInt(pageTable.getTotal().toString()));
        historyListEntry.setHasNextPage(pageTable.getHasNextPage());
        historyListEntry.setHasPreviousPage(pageTable.getHasPreviousPage());
        return historyListEntry;
    }

    public static List<History2> getTljMatchAchList(Date date) {
        List<History2> historyList = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(HttpAddr.MATCH_GET_MATCH_ACH_BY_DATE, String.valueOf(date), UserState.token));
        JSONArray result = jsonObject.getJSONArray("result");
        if (jsonObject.getString("message").equals("获取成功")) {
            for (Object historyJson : result) {
                JSON json = JSON.parseObject(historyJson.toString());
                historyList.add(JSONObject.toJavaObject(json, History2.class));
            }
        }
        return historyList;
    }
}
