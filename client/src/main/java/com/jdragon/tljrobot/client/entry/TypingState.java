package com.jdragon.tljrobot.client.entry;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.utils.common.Timer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class TypingState {
    private TypingState() {
    }

    /**
     * 跟打状态
     */
    public static boolean typingState;
    public static int typeLength;

    /**
     * 英词长度
     */
    public static int enWordLength;

    /**
     * 键数
     */

    public static int keyNumber;

//    public static double speed;//速度
//    public static double keySpeed;//击键
//    public static double keyLength;//码长
    /**
     * 看打多字
     */
    public static int lookMore;
    /**
     * 看打少字
     */
    public static int lookMiss;
    /**
     * 看打错字
     */
    public static int lookMis;
    /**
     * 一共错字
     */
    public static int mistake;

    /**
     * 英词错词
     */
    public static int enMistake;

    /**
     * 退格
     */
    public static int deleteNumber;
    /**
     * 回改
     */
    public static int deleteTextNumber;
    /**
     * 选重
     */
    public static int repeat;
    /**
     * 打词数
     */
    public static int typeWordsNum;
    /**
     * 计算键法变量
     * 左键数
     * 右键数
     * 空格
     */
    public static int left;
    public static int right;
    public static int space;
    /**
     * 暂停状态
     */
    public static boolean pause;
    /**
     * 发文状态
     */
    public static int sendArticle;
    /**
     * 日赛状态
     */
    public static Boolean dailyCompetition = false;
    /**
     * 计时器
     */
    public final static Timer timer = new Timer();
    /**
     * 击键记录
     */
    public static StringBuilder record = new StringBuilder();
    /**
     * 错误记录
     */
    public static List<String> mistakeList = new ArrayList<>();

    public static List<WordsState> typeWordsList = new ArrayList<>();

    /**
     * 记录打词
     */
    public static void init() {
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
        lookMis = 0;
        lookMiss = 0;
        lookMore = 0;
        record = new StringBuilder();
        timer.setStartTime(0L);
        timer.setEndTime(0L);
        mistakeList.clear();
        typeWordsList.clear();
        pause = false;
    }

    public static void typingStart() {
        timer.timeStart();
    }

    public static void typingEnd() {
        timer.timeEnd();
    }

    public static void pauseStart() {
        timer.pauseTimeStart();
    }

    public static void pauseEnd() {
        timer.pauseTimeEnd();
    }

    public static double getSpeed() {
        double minute = timer.getMinute();
        if (LocalConfig.errorPunishment) {
            return doubleKeyTwo((1.00 * typeLength - 5.00 * mistake) / minute);
        } else {
            return doubleKeyTwo((1.00 * (typeLength - mistake)) / minute);
        }
    }

    public static double getEnSpeed() {
        double minute = timer.getMinute();
        if (LocalConfig.errorPunishment) {
            return doubleKeyTwo((1.00 * enWordLength - 5.00 * enMistake) / minute);
        } else {
            return doubleKeyTwo((1.00 * (enWordLength - enMistake)) / minute);
        }
    }

    public static double getSpeedNoMistake() {
        double minute = timer.getMinute();
        return doubleKeyTwo((1.00 * typeLength) / minute);
    }


    public static double getEnSpeedNoMistake() {
        double minute = timer.getMinute();
        return doubleKeyTwo((1.00 * enWordLength) / minute);
    }

    public static double getKeyLength() {
        if (typeLength == 0) {
            return keyNumber;
        } else {
            return doubleKeyTwo(1.00 * keyNumber / typeLength);
        }
    }

    public static double getKeySpeed() {
        return doubleKeyTwo(1.00 * keyNumber / timer.getSecond());
    }

    public static double getKeyAccuracy() {
//        return doubleKeyTwo(typeLength/typeWordsNum);
        return doubleKeyTwo(100.00 * (keyNumber - deleteNumber * 2 - deleteTextNumber
                * Article.getArticleSingleton().getShortCodeEntity().getArticleAverCodes()) / keyNumber);
    }

    public static int getRetry() {
        return Article.getArticleSingleton().getRetry();
    }

    public static double getWordRate() {
        return doubleKeyTwo(100.00 * typeWordsNum / (typeLength + deleteTextNumber));
    }

    public static double getRepeatRate() {
        return doubleKeyTwo(100.00 * repeat / typeLength);
    }

    public static double getKeyMethod() {
        return doubleKeyTwo(100.00 * left / right);
    }

    private static double doubleKeyTwo(double target) {
        return Double.parseDouble(String.format("%.2f", target));
    }

    //打词时的状态
    @Data
    public static class WordsState {
        private double speed;//临时速度
        private double keySpeed;//临时击键
        private double instantaneousSpeed;//瞬时速度
        private double time;//上屏时间
        private String words;

        public WordsState(double speed, double keySpeed, double instantaneousSpeed, String words, double time) {
            this.speed = speed;
            this.keySpeed = keySpeed;
            this.instantaneousSpeed = instantaneousSpeed;
            this.words = words;
            this.time = time;
        }
    }
}
