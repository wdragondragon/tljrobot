package com.jdragon.tljrobot.robot.newTyping;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.robot.newTyping.config.HttpAddr;
import com.jdragon.tljrobot.robot.typing.Tools.RegexText;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;

/**
 * Create by Jdragon on 2020.02.05
 */
public class QueryUser extends IcqListener {
    @EventHandler
    public void carryGroupMessage(EventGroupMessage eventGroupMessage) throws UnsupportedEncodingException {
        String message = eventGroupMessage.getMessage();

        if(message.equals("#长流详情")){
            JSONObject jsonObject = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.QUERY_TLJ_ALL_TYPE_INFO));
            String retMessage = jsonObject.getString("message");
            if(retMessage.equals("获取成功")){
                JSONObject result = jsonObject.getJSONObject("result");
                int typeNum = result.getIntValue("num");
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
                        "\n全体共跟打次数：" + typeNum +
                        "\n全体在跟打上累计的有效时间：" + String.format("%.2f",time) + "秒"+
                        "\n换算时间："+year+"年"+month+"月"+day+"天"+hour+"时"+minute+"分"+second+"秒"+
                        "\n今日跟打最多："+username + "打了"+userMaxNum+"字");
            }else{
                eventGroupMessage.respond(retMessage);
            }
        }
        String[] s = message.split(" ");
        if(s.length==2){
            if(s[0].equals("#查询")){
                Matcher m = RegexText.isAt(s[1]);
                if(m.find()){
                    System.out.println(m.group(1));
                    long queryQq = Long.parseLong(m.group(1));
                    JSONObject jsonObject = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.QUERY_GROUP_AVR_TYPE_INFO, String.valueOf(queryQq)));
                    String retMessage = jsonObject.getString("message");
                    if(retMessage.equals("获取成功")){
                        JSONObject result = jsonObject.getJSONObject("result");
                        double keyLength = result.getDoubleValue("keyLength");
                        double keySpeed = result.getDoubleValue("keySpeed");
                        double speed = result.getDoubleValue("speed");
                        int num = result.getIntValue("num");
                        eventGroupMessage.respond("Q号："+queryQq+
                                "\n上屏赛文成绩次数："+ num+
                                "\n平均速度："+ RegexText.FourOutFiveIn(speed)+
                                " 平均击键："+RegexText.FourOutFiveIn(keySpeed)+
                                " 平均码长："+RegexText.FourOutFiveIn(keyLength));
                    }else {
                        eventGroupMessage.respond(retMessage);
                    }
                }
            }else if(s[0].equals("#长流")){
                JSONObject jsonObject = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.QUERY_TLJ_AVR_TYPE_INFO, URLEncoder.encode(s[1],"UTF-8")));
                String retMessage = jsonObject.getString("message");
                if(retMessage.equals("获取成功")){
                    JSONObject result = jsonObject.getJSONObject("result");
                    int allNum = result.getIntValue("allNum");
                    int rightNum = result.getIntValue("rightNum");
                    int misNum = result.getIntValue("misNum");
                    int dateNum = result.getIntValue("dateNum");
                    double keyLength = result.getDoubleValue("keyLength");
                    double keySpeed = result.getDoubleValue("keySpeed");
                    double speed = result.getDoubleValue("speed");
                    int num = result.getIntValue("num");
                    eventGroupMessage.respond("用户名："+s[1]+
                            "\n总跟打次数："+ num+
                            "\n总："+allNum+" 对："+rightNum+" 错："+misNum+" 今："+dateNum+
                            "\n平均速度："+ RegexText.FourOutFiveIn(speed)+
                            " 平均击键："+RegexText.FourOutFiveIn(keySpeed)+
                            " 平均码长："+RegexText.FourOutFiveIn(keyLength));
                }else {
                    eventGroupMessage.respond(retMessage);
                }
            }
        }
    }
    @Test
    public void test() throws UnsupportedEncodingException {
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.QUERY_TLJ_ALL_TYPE_INFO));
        String retMessage = jsonObject.getString("message");
        if(retMessage.equals("获取成功")){
            JSONObject result = jsonObject.getJSONObject("result");
            int typeNum = result.getIntValue("num");
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
            System.out.println("拖拉机详情：\n注册有"+userNum+"个账号" +
                    "\n现在线人数：" + onlineNum +
                    "\n跟打器跟打总字数：" + userTypeNum + " 对：" +userRightNum +" 错："+userMisNum +
                    "\n跟打器今日跟打总字数：" + userDateNum +
                    "\n全体平均速度：" + String.format("%.2f",(number/time)*60) +
                    "\n全体共跟打次数：" + typeNum +
                    "\n全体在跟打上累计的有效时间：" + String.format("%.2f",time) + "秒"+
                    "\n换算时间："+year+"年"+month+"月"+day+"天"+hour+"时"+minute+"分"+second+"秒"+
                    "\n今日跟打最多："+username + "打了"+userMaxNum+"字");
        }else{
            System.out.println(retMessage);
        }
    }
}
