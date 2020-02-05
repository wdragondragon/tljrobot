package com.jdragon.tljrobot.robot.newTyping.tools;

import com.jdragon.tljrobot.robot.newTyping.entry.RobotHistory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by Jdragon on 2020.01.21
 */
public class Regex {
    public static int getParagraph(String str) {
        int strLength = str.length();
        str = str.substring(0, Math.min(strLength, 10));
        Pattern p = Pattern.compile("第(.*?)段");//正则表达式，取=和|之间的字符串，不包括=和|
        Matcher m = p.matcher(str);
        if (m.find()) {
            //m.group(1)不包括这两个字符
            System.out.println(Integer.valueOf(m.group(1)));
            return Integer.valueOf(m.group(1));
        } else return -1;
    }
    public static RobotHistory getGradeMap(String event){
        RobotHistory robotHistory = new RobotHistory();
        try {
            int speedStart = event.indexOf("速度");
            int speedEnd = event.indexOf(" ", speedStart);
            int temp = event.indexOf("/", speedStart);
            if (temp < speedEnd && temp != -1) speedEnd = temp;
            int keyLengthStart = event.indexOf("码长");
            int keySpeedStart = event.indexOf("击键");
            int deleteNumStart = event.indexOf("退格");
            int deleteTextStart = event.indexOf("回改");
            int repeatNumStart = event.indexOf("选重");
            int mistakeStart = event.indexOf("错字");
            int keyAccuracyStart = event.indexOf("键准");
            double speed = Double.parseDouble(event.substring(speedStart + 2, speedEnd));
            if(speed>1000)speed = 0;
            robotHistory.setSpeed(speed);
            if (keySpeedStart != -1) {
                int keySpeedEnd = event.indexOf(" ", keySpeedStart);
                double keySpeed= Double.parseDouble(event.substring(keySpeedStart + 2, keySpeedEnd));
                robotHistory.setKeySpeed(keySpeed);
            }else robotHistory.setKeyLength(-1.0);
            if (keyLengthStart != -1) {
                int keyLengthEnd = event.indexOf(" ", keyLengthStart);
                double keyLength = Double.parseDouble(event.substring(keyLengthStart + 2, keyLengthEnd));
                robotHistory.setKeyLength(keyLength);
            }else robotHistory.setKeyLength(-1.0);

            if (deleteNumStart != -1) {
                int deleteNumEnd = event.indexOf(" ", deleteNumStart);
                int deleteNum = Integer.parseInt(event.substring(deleteNumStart + 2, deleteNumEnd));
                robotHistory.setDeleteNum(deleteNum);
            }else robotHistory.setDeleteNum(-1);
            if (deleteTextStart != -1) {
                int deleteTextEnd1 = event.indexOf(" ", deleteTextStart);
                int deleteTextEnd2 = event.indexOf("(",deleteTextStart);
                int deleteText;
                if(deleteTextEnd1>deleteTextEnd2&&deleteTextEnd2!=-1)
                    deleteText = Integer.parseInt(event.substring(deleteTextStart + 2, deleteTextEnd2));
                else if(deleteTextEnd1!=-1)
                    deleteText = Integer.parseInt(event.substring(deleteTextStart + 2, deleteTextEnd1));
                else deleteText = -1;
                robotHistory.setDeleteText(deleteText);
            }else robotHistory.setDeleteText(-1);
            if (mistakeStart != -1) {
                int mistakeEnd = event.indexOf(" ", mistakeStart);
                int mistake = Integer.parseInt(event.substring(mistakeStart + 2, mistakeEnd));
                robotHistory.setMistake(mistake);
            }else robotHistory.setMistake(-1);
            if(keyAccuracyStart != -1){
                int keyAccuracyEnd = event.indexOf("%",keyAccuracyStart);
                double keyAccuracy = Double.parseDouble(event.substring(keyAccuracyStart + 2 ,keyAccuracyEnd));
                robotHistory.setKeyAccuracy(keyAccuracy);
            }else robotHistory.setKeyAccuracy(-1.0);
            if (repeatNumStart != -1) {
                if(event.indexOf("选重率")!=repeatNumStart) {
                    int selectEnd = event.indexOf(" ", repeatNumStart);
                    int repeatNum = Integer.parseInt(event.substring(repeatNumStart + 2, selectEnd));
                    robotHistory.setRepeatNum(repeatNum);
                }else robotHistory.setRepeatNum(-1);
            }else robotHistory.setRepeatNum(-1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return robotHistory;
    }
}
