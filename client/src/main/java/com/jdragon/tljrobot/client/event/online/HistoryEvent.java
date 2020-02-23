package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.*;
import com.jdragon.tljrobot.client.event.threadEvent.CountMatchThread;
import com.jdragon.tljrobot.tljutils.HttpUtil;

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by Jdragon on 2020.01.18
 */
public class HistoryEvent {
    public static void getMatch(){
        HashMap<String,String> params = new HashMap<>();
        params.put("isMobile","0");
        JSONObject jsonObject = JSON.parseObject(HttpUtil.doGetParam(HttpAddr.MATCH_GET_TODAY, params,UserState.token));
        String message = jsonObject.getString(Constant.RESPONSE_MESSAGE);
        if("获取成功".equals(message)){
            /**
             * 获取数据示范
             * {"code":200,"message":"获取成功","result":{"id":0,"holdDate":"2020-01-18","article":{"id":1,"title":"1","content":"1"}}}
             */
            JSONObject article = jsonObject.getJSONObject("result").getJSONObject("article");
            Article.getArticleSingleton(0,article.getString("title"),article.getString("content"));
            SwingSingleton.typingText().setEditable(false);
            new CountMatchThread().start();
        }else{
            JOptionPane.showMessageDialog(null,message);
        }
    }
    public static HistoryList getHistoryByPage(int page){
        List<History> historyList = new ArrayList<>();
        HistoryList historyListEntry = new HistoryList();

        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(HttpAddr.ME_HISTORY_ADDR, String.valueOf(page), UserState.token));
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray historyListJson = result.getJSONArray("list");
        int total = result.getIntValue("total");
        int pageNum = result.getIntValue("pageNum");
        int pages = result.getIntValue("pages");
        boolean hasNextPage = result.getBoolean("hasNextPage");
        boolean hasPreviousPage = result.getBoolean("hasPreviousPage");
        for(Object historyJson:historyListJson){
            JSON json = JSON.parseObject(historyJson.toString());
            historyList.add(JSONObject.toJavaObject(json,History.class));
        }
        historyListEntry.setHistoryList(historyList);
        historyListEntry.setHasNextPage(hasNextPage);
        historyListEntry.setHasPreviousPage(hasPreviousPage);
        historyListEntry.setTotal(total);
        historyListEntry.setPageNum(pageNum);
        historyListEntry.setPages(pages);
        return historyListEntry;
    }
    public static List<History2> getTljMatchAchList(Date date){
        List<History2> historyList = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(HttpAddr.MATCH_GET_MATCH_ACH_BY_DATE, String.valueOf(date),UserState.token));
        JSONArray result = jsonObject.getJSONArray("result");
        if(jsonObject.getString("message").equals("获取成功")) {
            for (Object historyJson : result) {
                JSON json = JSON.parseObject(historyJson.toString());
                historyList.add(JSONObject.toJavaObject(json, History2.class));
            }
        }
        return historyList;
    }
}
