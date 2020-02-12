package com.jdragon.tljrobot.robot.newTyping;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.util.Md5Utils;
import com.jdragon.tljrobot.robot.club.robot;
import com.jdragon.tljrobot.robot.newTyping.config.HttpAddr;
import com.jdragon.tljrobot.robot.newTyping.entry.RobotHistory;
import com.jdragon.tljrobot.robot.newTyping.entry.TljHistory;
import com.jdragon.tljrobot.robot.newTyping.tools.DrawImg;
import com.jdragon.tljrobot.robot.newTyping.tools.GroupCache;
import com.jdragon.tljrobot.robot.newTyping.tools.Regex;
import com.jdragon.tljrobot.robot.typing.Tools.RegexText;
import com.jdragon.tljrobot.tljutils.DateUtil;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Jdragon on 2020.01.21
 */
public class RobotGroupClient extends IcqListener {
    @EventHandler
    public void carryGroupMessage(EventGroupMessage eventGroupMessage){
        String message = eventGroupMessage.getMessage();
        long qq = eventGroupMessage.getSenderId();
        long groupId = eventGroupMessage.getGroupId();
        if (qq==robot.xiaochaiQ) {
            RegexText rgt = new RegexText();
            String[] Com = rgt.CarryCom(eventGroupMessage.getMessage());
            if(!Com[2].equals("999"))return;
            System.out.println("捕获赛文");
            if (Com[2].equals("999")) {
                System.out.println("捕获赛文");
                JSONObject articleJson = new JSONObject();
                articleJson.put("title",Com[0]);
                articleJson.put("content",Com[1]);
                JSONObject groupMatchJson = new JSONObject();
                groupMatchJson.put("holdDate",DateUtil.now().toString());
                groupMatchJson.put("groupId","522394334");
                groupMatchJson.put("article",articleJson);
                JSONObject jsonObject = JSONObject.parseObject(HttpUtil.doPostObject(HttpAddr.UPLOAD_GROUP_MATCH_ADDR,groupMatchJson));
                String retMessage = jsonObject.getString("message");
                if(retMessage.equals("上传成功")){
                    eventGroupMessage.respond("今日该群赛文收录成功");
                }
            }
        }else if(message.equals("#联赛")){
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
            eventGroupMessage.respond(resultUnionJson(false));
        }else if(message.equals("#联赛首打")){
            eventGroupMessage.respond(resultUnionJson(true));
        }else if (message.equals("#长流成绩")){
            //{"id":173,"userId":1,"typeDate":"2020-02-04","speed":123.0,"keySpeed":123.0,"keyLength":123.0,
            // "number":123,"deleteText":123,"deleteNum":123,"mistake":123,"repeatNum":123,"keyAccuracy":123.0,
            // "keyMethod":123.0,"wordRate":123.0,"time":123.0,"articleId":123,"paragraph":0,"userName":"谭宇"}
            eventGroupMessage.respond(resultTljJson());
        }else if(Regex.getParagraph(message)==999||Regex.getParagraph(message)==9999){
            if(!GroupCache.typeGroupMap.containsKey(groupId))return;
            RobotHistory robotHistory = Regex.getGradeMap(message);
            robotHistory.setQq(qq);
            robotHistory.setGroupId(groupId);
            robotHistory.setTypeDate(DateUtil.now());
            robotHistory.setMatch(Regex.getParagraph(message)==9999);
            JSONObject jsonObject = JSON.parseObject(HttpUtil.doPostObject(HttpAddr.UPLOAD_HISTORY,robotHistory));
            System.out.println(jsonObject);
        }else if(message.equals("#刷新名片")){
            GroupCache.refreshCardCache(eventGroupMessage.getHttpApi());
        }
        String[] params = message.split(" ");
        if(params.length==2){
            if(params[0].equals("#历史赛文")){
                JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(HttpAddr.GET_GROUP_MATCH_ADDR,"522394334",params[1]));
                String retMessage = jsonObject.getString("message");
                JSONObject articleJson = jsonObject.getJSONObject("result").getJSONObject("article");
                if(retMessage.equals("获取成功")){
                    String title = articleJson.getString("title");
                    String content = articleJson.getString("content");
                    String respond = title+"\n"+content+"\n-----第1段-共"+content.length()+"字";
                    eventGroupMessage.respond(respond);
                }else{
                    eventGroupMessage.respond(retMessage);
                }
            }
        }
    }
    public static String resultTljJson(){
        String resoleResult;
        String title = DateUtil.now().toString();
        String[] head = new String[]{"序号","名字","成绩","击键","码长","退格","回改","错字","选重","键准","键法","打词","设备"};
        JSONObject retJson = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.GET_TLJ_MATCH_RANK_ALL, title));
        String retMessage = retJson.getString("message");
        if(retMessage.equals("获取成功")) {
            List<TljHistory> tljHistories = new ArrayList<>();
            JSONArray resultJson = retJson.getJSONArray("result");
            for(Object achObj:resultJson){
                JSON achJson = JSON.parseObject(achObj.toString());
                tljHistories.add(JSON.toJavaObject(achJson,TljHistory.class));
            }
            String drawPath = DrawImg.tljMatchByHistories(title,head,tljHistories);
            if(drawPath.equals("生成图片出错"))
                resoleResult = drawPath;
            else
                resoleResult = new ComponentImage(drawPath).toString();
        }else{
            resoleResult = retMessage;
        }
        return resoleResult;
    }
    public static String resultUnionJson(boolean isFirst){
        String resoleResult;
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
            JSONObject retJson;
            if(isFirst)
                retJson = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.GET_UNION_FIRST_MATCH_RANK, DateUtil.now().toString()));
            else retJson = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.GET_UNION_MATCH_RANK, DateUtil.now().toString()));
            retMessage = retJson.getString("message");
            if(retMessage.equals("获取成功")){
                List<RobotHistory> robotHistories = new ArrayList<>();
                JSONArray resultJson = retJson.getJSONArray("result");
                for(Object achObj:resultJson){
                    JSON achJson = JSON.parseObject(achObj.toString());
                    robotHistories.add(JSON.toJavaObject(achJson,RobotHistory.class));
                }
                String drawPath = DrawImg.unionMatchByHistories(title,head,robotHistories);

                if(drawPath.equals("生成图片出错"))
                    resoleResult = drawPath;
                else
                    resoleResult = new ComponentImage(drawPath).toString();
            }else
                resoleResult = retMessage;
        }else
            resoleResult = retMessage;
        return resoleResult;
    }
    @Test
    public void test(){
        JSONObject retJson = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.GET_UNION_FIRST_MATCH_RANK, DateUtil.now().toString()));
        JSONObject retJson2 = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.GET_UNION_MATCH_RANK, DateUtil.now().toString()));
        System.out.println(retJson);
        System.out.println(retJson2);
        System.out.println(Md5Utils.getMD5("ihlq2020".getBytes()));
    }
}
