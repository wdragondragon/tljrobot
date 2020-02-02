package com.jdragon.tljrobot.robot.newTyping;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.robot.newTyping.config.HttpAddr;
import com.jdragon.tljrobot.robot.newTyping.entry.RobotHistory;
import com.jdragon.tljrobot.robot.newTyping.tools.DrawImg;
import com.jdragon.tljrobot.robot.newTyping.tools.Regex;
import com.jdragon.tljrobot.tljutils.DateUtil;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Jdragon on 2020.01.21
 */
public class RobotGroupClient {
    @EventHandler
    public void carryGroupMessage(EventGroupMessage eventGroupMessage){
        String message = eventGroupMessage.getMessage();
        long qq = eventGroupMessage.getSenderId();
        long groupId = eventGroupMessage.getGroupId();
        if(message.equals("#联赛")){
            //{"code":200,"message":"获取成功","result":{"id":0,"holdDate":"2020-01-22","articleId":1,"author":"321321","article":{"id":1,"title":"1","content":"1"}}}
            JSONObject retJson = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.GET_UNION_MATCH, DateUtil.now().toString()));
            String retMessage = retJson.getString("message");
            if(retMessage.equals("获取成功")) {
                System.out.println(retJson);
                JSONObject resultJson = retJson.getJSONObject("result");
                JSONObject articleJson = resultJson.getJSONObject("article");
                String author = resultJson.getString("author");
                String title = articleJson.getString("title");
                String content = articleJson.getString("content");
                String respond = title+" 投稿自QQ号："+author+"\n"+content+"\n-----第9999段-共"+content.length()+"字";
                eventGroupMessage.respond(respond);
            }else{
                eventGroupMessage.respond(retMessage);
            }
        }else if(message.equals("#联赛成绩")){
            String resoleResult;
            //{"code":200,"message":"获取成功","result":{"id":0,"holdDate":"2020-01-22","articleId":1,"author":"321321","article":{"id":1,"title":"1","content":"1"}}}
            JSONObject articleJson = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.GET_UNION_MATCH, DateUtil.now().toString()));
            String retMessage = articleJson.getString("message");
            if(retMessage.equals("获取成功")){
                String title = articleJson.getJSONObject("result").getJSONObject("article").getString("title");
                String[] head = new String[]{"序号","名字","来自","成绩","击键","码长","退格","回改","选重","错字","键准","重打"};
                /**
                 * {
                 * "result":
                 *          [{"qq":1061917196,"typeDate":"2020-01-22","mistake":0,"deleteNum":22,"groupId":1234567,"match":true,
                 *          "typeNum":2,"keyAccuracy":91.75,"speed":153.39,"keyLength":2.51,"deleteText":19,"id":39,"keySpeed":6.42,"repeatNum":33}],
                 * "code":200,"message":"获取成功"
                 * }
                 **/
                JSONObject retJson = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.GET_UNION_MATCH_RANK, DateUtil.now().toString()));
                System.out.println(retJson);
                retMessage = retJson.getString("message");
                if(retMessage.equals("获取成功")){
                    List<RobotHistory> robotHistories = new ArrayList<>();
                    JSONArray resultJson = retJson.getJSONArray("result");
                    for(Object achObj:resultJson){
                        JSON achJson = JSON.parseObject(achObj.toString());
                        robotHistories.add(JSON.toJavaObject(achJson,RobotHistory.class));
                    }
                    String drawPath = DrawImg.tljMatchByHistories(title,head,robotHistories);

                    if(drawPath.equals("生成图片出错"))
                        resoleResult = drawPath;
                    else
                        resoleResult = new ComponentImage(drawPath).toString();
                }else
                    resoleResult = "获取成绩失败";
            }else
                resoleResult = "今天没有赛文";
            eventGroupMessage.respond(resoleResult);
        }else if(Regex.getParagraph(message)==999||Regex.getParagraph(message)==9999){
            RobotHistory robotHistory = Regex.getGradeMap(message);
            robotHistory.setQq(qq);
            robotHistory.setGroupId(groupId);
            robotHistory.setTypeDate(DateUtil.now());
            robotHistory.setMatch(Regex.getParagraph(message)==9999);
            JSONObject jsonObject = JSON.parseObject(HttpUtil.doPostObject(HttpAddr.UPLOAD_HISTORY,robotHistory));
            System.out.println(jsonObject);
        }
    }
    @Test
    public void test(){
        //{"code":200,"message":"获取成功","result":{"id":0,"holdDate":"2020-01-22","articleId":1,"author":"321321","article":{"id":1,"title":"1","content":"1"}}}
        JSONObject retJson = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.GET_UNION_MATCH, DateUtil.now().toString()));
        String retMessage = retJson.getString("message");
        if(retMessage.equals("获取成功")) {
            System.out.println(retJson);
            JSONObject resultJson = retJson.getJSONObject("result");
            JSONObject articleJson = resultJson.getJSONObject("article");
            String author = resultJson.getString("author");
            String title = articleJson.getString("title");
            String content = articleJson.getString("content");
            String respond = title+" 投稿自QQ号："+author+"\n"+content+"\n-----第9999段-共"+content.length()+"字";
            System.out.println(respond);
        }else{
            System.out.println(retMessage);
        }
    }
}
