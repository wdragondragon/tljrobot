package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.OnlineConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.*;
import com.jdragon.tljrobot.client.event.threadEvent.CountMatch;
import com.jdragon.tljrobot.tljutils.DateUtil;
import com.jdragon.tljrobot.tljutils.HttpUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by Jdragon on 2020.01.18
 */
public class HistoryEvent {
    public static void getMatch(){
        JSONObject jsonObject = JSON.parseObject(HttpUtil.doGet(OnlineConfig.MATCH_GET_TODAY, UserState.token));
        String message = jsonObject.getString(Constant.RESPONSE_MESSAGE);
        if(message.equals("获取成功")){
            /**
             * 获取数据示范
             * {"code":200,"message":"获取成功","result":{"id":0,"holdDate":"2020-01-18","article":{"id":1,"title":"1","content":"1"}}}
             */
            JSONObject article = jsonObject.getJSONObject("result").getJSONObject("article");
            Article.getArticleSingleton(0,article.getString("title"),article.getString("content"));
            SwingSingleton.TypingText().setEditable(false);
            new CountMatch().start();
        }else{
            JOptionPane.showMessageDialog(null,message);
        }
    }
    public static String uploadMatchAch(){
        History history = getHistoryEntry();
        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPostObject(OnlineConfig.MATCH_UPLOAD_TLJ_MATCH_ACH,history, UserState.token));
        return jsonObject.getString("message");
    }
    public static String uploadHistory(){
        History history = getHistoryEntry();
        Map<String,String> params = new HashMap();
        params.put("content",Article.getArticleSingleton().getArticle());
        params.put("title",Article.getArticleSingleton().getTitle());
        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPostObjectAndParams(OnlineConfig.HISTORY_UPLOAD_HISTORY,params,history,UserState.token));
        System.out.println(jsonObject.toJSONString());
        return jsonObject.getString("message");
    }
    public static History getHistoryEntry(){
        History history = new History();
        history.setSpeed(TypingState.getSpeed());
        history.setKeySpeed(TypingState.getKeySpeed());
        history.setKeyLength(TypingState.getKeyLength());
        history.setNumber(Article.getArticleSingleton().getArticle().length());
        history.setDeleteNum(TypingState.deleteNumber);
        history.setDeleteText(TypingState.deleteTextNumber);
        history.setKeyAccuracy(TypingState.getKeyAccuracy());
        history.setKeyMethod(TypingState.getKeyMethod());
        history.setParagraph(0);
        history.setMistake(TypingState.mistake);
        history.setRepeatNum(TypingState.repeat);
        history.setTime(TypingState.timer.getSecond());
        history.setWordRate(TypingState.getWordRate());
        history.setParagraph(Article.getArticleSingleton().getParagraph());
        history.setTypeDate(DateUtil.now());
        return history;
    }
    public static HistoryList getHistoryByPage(int page){
        List<History> historyList = new ArrayList<>();
        HistoryList historyListEntry = new HistoryList();

        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(OnlineConfig.ME_HISTORY_ADDR, String.valueOf(page), UserState.token));
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
}
