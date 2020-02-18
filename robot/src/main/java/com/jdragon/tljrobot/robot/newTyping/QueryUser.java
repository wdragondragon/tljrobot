package com.jdragon.tljrobot.robot.newTyping;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.robot.newTyping.config.HttpAddr;
import com.jdragon.tljrobot.robot.newTyping.tools.GroupCache;
import com.jdragon.tljrobot.robot.typing.Tools.Createimg;
import com.jdragon.tljrobot.robot.typing.Tools.RegexText;
import com.jdragon.tljrobot.tljutils.HttpUtil;

import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;

/**
 * Create by Jdragon on 2020.02.05
 */
public class QueryUser extends IcqListener {
    @EventHandler
    public void carryGroupMessage(EventGroupMessage eventGroupMessage) throws Exception {
        String message = eventGroupMessage.getMessage();
        long groupId = eventGroupMessage.getGroupId();
        if(message.equals("#长流详情")){
            JSONObject jsonObject = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.QUERY_TLJ_ALL_TYPE_INFO));
            String retMessage = jsonObject.getString("message");
            if(retMessage.equals("获取成功")){
                JSONObject result = jsonObject.getJSONObject("result");
                int count = result.getIntValue("count");
                int onlineNum = result.getIntValue("onlineNum");
                int userMaxNum = result.getIntValue("userMaxNum");
                String username = result.getString("username");
                int userNum = result.getIntValue("userNum");
                int userTypeNum = result.getIntValue("userTypeNum");
                int userRightNum = result.getIntValue("userRightNum");
                int userMisNum = result.getIntValue("userMisNum");
                int userDateNum = result.getIntValue("userDateNum");
                int number = result.getIntValue("number");
                double time = result.getDoubleValue("time");
                int year = (int)time/(60*60*24*30*12);
                int month = ((int)time/(60*60*24*30))%12;
                int day = ((int)time/(60*60*24))%30;
                int hour = ((int)time/(60*60))%60;
                int minute = ((int)time/60)%60;
                int second = ((int)time)%60;
                eventGroupMessage.respond("拖拉机详情：\n注册有"+userNum+"个账号" +
                        "\n现在线人数：" + onlineNum +
                        "\n跟打器跟打总字数：" + userTypeNum + " 对：" +userRightNum +" 错："+userMisNum +
                        "\n跟打器今日跟打总字数：" + userDateNum +
                        "\n全体平均速度：" + String.format("%.2f",(number/time)*60) +
                        "\n全体共跟打次数：" + count +
                        "\n全体在跟打上累计的有效时间：" + String.format("%.2f",time) + "秒"+
                        "\n换算时间："+year+"年"+month+"月"+day+"天"+hour+"时"+minute+"分"+second+"秒"+
                        "\n今日跟打最多："+username + "打了"+userMaxNum+"字");
            }else{
                eventGroupMessage.respond(retMessage);
            }
        }else if(message.equals("#长流均速排名")){
            JSONObject jsonObject = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.QUERY_TLJ_TYPE_INFO_LIST_ORDER_BY_SPEED));
            String retMessage = jsonObject.getString("message");
            if(retMessage.equals("获取成功")){
                String title = "长流均速排行";
                String[] head = new String[]{"序号","名字","跟打次数","平均速度","平均击键","平均码长","总字数","对字数","错字数","日字数"};

                List<List<List<String>>> allValue = new ArrayList<>();
                List<List<String>> contentArray = new ArrayList<>();
                List<String[]> heads = new ArrayList<>();
                List<String> titles = new ArrayList<>();
                heads.add(head);
                titles.add(title);
                List<Double> keySpeedRankList = new ArrayList<>();
                List<Double> keyLengthRankList = new ArrayList<>();
                List<Double> numRankList = new ArrayList<>();
                List<Double> rightNumRankList = new ArrayList<>();
                List<Double> misNumRankList = new ArrayList<>();
                List<Double> dateNumRankList = new ArrayList<>();
                List<Double> countRankList = new ArrayList<>();
                JSONArray TljAvgTypeInfoListJson = jsonObject.getJSONArray("result");
                for(Object object:TljAvgTypeInfoListJson){
                    JSONObject TljAvgTypeInfo = (JSONObject)object;
                    String username = TljAvgTypeInfo.getString("username");
                    int count = TljAvgTypeInfo.getIntValue("count");
                    double speed = TljAvgTypeInfo.getDoubleValue("speed");
                    double keySpeed = TljAvgTypeInfo.getDoubleValue("keySpeed");
                    double keyLength  = TljAvgTypeInfo.getDoubleValue("keyLength");
                    int num = TljAvgTypeInfo.getIntValue("num");
                    int rightNum = TljAvgTypeInfo.getIntValue("rightNum");
                    int misNum = TljAvgTypeInfo.getIntValue("misNum");
                    int dateNum = TljAvgTypeInfo.getIntValue("dateNum");
                    keySpeedRankList.add(keySpeed);
                    keyLengthRankList.add(keyLength);
                    numRankList.add((double) num);
                    rightNumRankList.add((double) rightNum);
                    misNumRankList.add((double) misNum);
                    dateNumRankList.add((double) dateNum);
                    countRankList.add((double) count);
                    contentArray.add(Arrays.asList(username,String.valueOf(count),String.format("%.2f",speed),String.format("%.2f",keySpeed),String.format("%.2f",keyLength)
                            ,String.valueOf(num),String.valueOf(rightNum),String.valueOf(misNum),String.valueOf(dateNum)));
                }
                keySpeedRankList.sort(Collections.reverseOrder());
                Collections.sort(keyLengthRankList);
                numRankList.sort(Collections.reverseOrder());
                rightNumRankList.sort(Collections.reverseOrder());
                misNumRankList.sort(Collections.reverseOrder());
                dateNumRankList.sort(Collections.reverseOrder());
                countRankList.sort(Collections.reverseOrder());
                allValue.add(contentArray);
                HashMap<Integer, List<Double>> rankMap = new HashMap<>();
                rankMap.put(2,countRankList);
                rankMap.put(4,keySpeedRankList);
                rankMap.put(5,keyLengthRankList);
                rankMap.put(6,numRankList);
                rankMap.put(7,rightNumRankList);
                rankMap.put(8,misNumRankList);
                rankMap.put(9,dateNumRankList);
                String path;
                try {
                    path = Createimg.graphicsGeneration(allValue,titles,heads,null,heads.get(0).length,rankMap);
                } catch (Exception e) {
                    path = "生成图片出错";
                    e.printStackTrace();
                }
                if(path.equals("生成图片出错")){
                    eventGroupMessage.respond(path);
                }else{
                    eventGroupMessage.respond(new ComponentImage(path).toString());
                }
            }else{
                eventGroupMessage.respond(retMessage);
            }
        }
        String[] s = message.split(" ");
        if(s.length==2){
            switch (s[0]) {
                case "#查询": {
                    Matcher m = RegexText.isAt(s[1]);
                    if (m.find()) {
                        System.out.println(m.group(1));
                        long queryQq = Long.parseLong(m.group(1));
                        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.QUERY_GROUP_AVR_TYPE_INFO, String.valueOf(queryQq)));
                        String retMessage = jsonObject.getString("message");
                        if (retMessage.equals("获取成功")) {
                            JSONObject result = jsonObject.getJSONObject("result");
                            double keyLength = result.getDoubleValue("keyLength");
                            double keySpeed = result.getDoubleValue("keySpeed");
                            double speed = result.getDoubleValue("speed");
                            int num = result.getIntValue("num");
                            int chatNum = result.getIntValue("chatNum");
                            eventGroupMessage.respond("Q号：" + queryQq +
                                    "\n上屏赛文成绩次数：" + num +
                                    "\n平均速度：" + RegexText.FourOutFiveIn(speed) +
                                    " 平均击键：" + RegexText.FourOutFiveIn(keySpeed) +
                                    " 平均码长：" + RegexText.FourOutFiveIn(keyLength) +
                                    "\n水群字数：" + chatNum);
                        } else {
                            eventGroupMessage.respond(retMessage);
                        }
                    }
                    break;
                }
                case "#长流":
                    JSONObject jsonObject = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.QUERY_TLJ_AVR_TYPE_INFO, URLEncoder.encode(s[1], "UTF-8")));
                    String retMessage = jsonObject.getString("message");
                    if (retMessage.equals("获取成功")) {
                        JSONObject result = jsonObject.getJSONObject("result");
                        int num = result.getIntValue("num");
                        int rightNum = result.getIntValue("rightNum");
                        int misNum = result.getIntValue("misNum");
                        int dateNum = result.getIntValue("dateNum");
                        double keyLength = result.getDoubleValue("keyLength");
                        double keySpeed = result.getDoubleValue("keySpeed");
                        double speed = result.getDoubleValue("speed");
                        int count = result.getIntValue("count");
                        eventGroupMessage.respond("用户名：" + s[1] +
                                "\n总跟打次数：" + count +
                                "\n总：" + num + " 对：" + rightNum + " 错：" + misNum + " 今：" + dateNum +
                                "\n平均速度：" + RegexText.FourOutFiveIn(speed) +
                                " 平均击键：" + RegexText.FourOutFiveIn(keySpeed) +
                                " 平均码长：" + RegexText.FourOutFiveIn(keyLength));
                    } else {
                        eventGroupMessage.respond(retMessage);
                    }
                    break;
                case "#名片": {
                    Matcher m = RegexText.isAt(s[1]);
                    if (m.find()) {
                        long queryQq = Long.parseLong(m.group(1));
                        String string = GroupCache.groupCardCache.get(groupId).get(queryQq);
                        eventGroupMessage.respond(string);
                    }
                    break;
                }
                default:break;
            }
        }
    }
}
