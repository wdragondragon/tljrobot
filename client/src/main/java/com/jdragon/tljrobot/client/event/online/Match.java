package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.OnlineConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.History;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.threadEvent.CountMatch;
import com.jdragon.tljrobot.tljutils.HttpUtil;

import javax.swing.*;

/**
 * Create by Jdragon on 2020.01.18
 */
public class Match {
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
    public static void uploadAch(){
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
        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPostObject(OnlineConfig.MATCH_UPLOAD_ACH,history, UserState.token));
        String message = jsonObject.getString("message");
        JOptionPane.showMessageDialog(null,message);
    }
}
