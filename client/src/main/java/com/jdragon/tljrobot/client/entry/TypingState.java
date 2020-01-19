package com.jdragon.tljrobot.client.entry;

import com.jdragon.tljrobot.client.utils.common.Timer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class TypingState {
    private TypingState(){}
    public static boolean typingState;//跟打状态
    public static int typeLength;
    public static int keyNumber;//键数

//    public static double speed;//速度
//    public static double keySpeed;//击键
//    public static double keyLength;//码长

    public static int mistake;//错字
    public static int deleteNumber;//退格
    public static int deleteTextNumber;//回改
    public static int repeat;//选重
    public static int typeWordsNum;//打词数
    //计算键法变量
    public static int left;//左键数
    public static int right;//右键数
    public static int space;//空格

    public static boolean pause;//暂停状态

    public static int sendArticle;//发文状态

    public static Boolean dailyCompetition = false;//日赛状态

    public final static Timer timer = new Timer();//计时器
    //击键记录
    public static StringBuilder record = new StringBuilder();
    //错误记录
    public static List<String> mistakeList = new ArrayList<>();
    //
    public static List<WordsState> typeWordsList = new ArrayList<>();
    //记录打词
    public static void init(){
        typingState = false;
        keyNumber = 0;
        mistake = 0;
        deleteNumber = 0;
        deleteTextNumber = 0;
        repeat = 0;
        left = 0;
        right = 0;
        space = 0;
        typeWordsNum = 0;
        record = new StringBuilder();
        timer.setStartTime(0L);
        timer.setEndTime(0L);
        mistakeList.clear();
        typeWordsList.clear();
    }
    public static void typingStart(){
        timer.timeStart();
    }
    public static void typingEnd(){
        timer.timeEnd();
    }
    public static void pauseStart(){
        timer.pauseTimeStart();
    }
    public static void pauseEnd(){
        timer.pauseTimeEnd();
    }
    public static double getSpeed(){
        double minute = timer.getMinute();
//        speed = (typeLength*1.0-5*mistake)/minute;
        return doubleKeyTwo((1.00*typeLength-5.00*mistake)/minute);
    }
    public static double getKeyLength(){
        if(typeLength==0)return keyNumber;
        else return doubleKeyTwo(1.00*keyNumber/typeLength);
    }
    public static double getKeySpeed(){
        return doubleKeyTwo(1.00*keyNumber/timer.getSecond());
    }
    public static double getKeyAccuracy(){
//        return doubleKeyTwo(typeLength/typeWordsNum);
        return doubleKeyTwo(100.00*(keyNumber - deleteNumber * 2 - deleteTextNumber
                * Article.getArticleSingleton().getShortCodeEntity().getArticleAverCodes())/keyNumber);
    }
    public static double getWordRate(){
        return doubleKeyTwo(100.00*typeWordsNum / (typeLength + deleteTextNumber));
    }
    public static double getRepeatRate(){
        return doubleKeyTwo(100.00*repeat/typeLength);
    }
    public static double getKeyMethod(){
        return doubleKeyTwo(100.00*left/right);
    }
    private static double doubleKeyTwo(double target){
        return Double.parseDouble(String.format("%.2f", target));
    }
    //打词时的状态
    @Data
    public static class WordsState {
        private double speed;//临时速度
        private double keySpeed;//临时击键
        private double instantaneousSpeed;//瞬时速度
        private String words;
        public WordsState(double speed,double keySpeed,double instantaneousSpeed,String words){
            this.speed = speed;
            this.keySpeed = keySpeed;
            this.instantaneousSpeed = instantaneousSpeed;
            this.words = words;
        }
    }
}
