package com.jdragon.tljrobot.client.config;

import com.jdragon.tljrobot.client.utils.core.IniAccess;

import java.awt.*;

import static com.jdragon.tljrobot.client.constant.Constant.CODE_TABLE;
import static com.jdragon.tljrobot.client.constant.Constant.FOLLOW_PLAY_PATTERN;
public class LocalConfig {
    public static String iniFilePath = "../set/config.ini";

    public static String configSection = "config";

    public static String typeDocName = "typeDoc";


    public static Integer windowX = 100;
    public static Integer windowY = 100;
    public static Integer windowWidth = 710;
    public static Integer windowHeight = 520;

    public static Color UIBackgroundColor = new Color(238, 238, 238);

    public static Color typingBackgroundColor = new Color(238, 238, 238);
    public static Color watchingBackgroundColor = new Color(238, 238, 238);

    public static Color rightColor = Color.GRAY;//对字颜色
    public static Color mistakeColor = Color.RED;//错字颜色
    public static Color threeCodeColor = Color.BLUE;//三码词颜色
    public static Color twoCodeColor = Color.ORANGE;//二码词颜色
    public static Color fourCodeColor = new Color(128, 138, 135);//四码字

    public static String family = "微软雅黑";//字型
    public static Integer fontSize = 30;//字体大小
    public static Integer typePageCount = 500;//页字数

    public static String codeTable = CODE_TABLE;//码表
    public static String typingPattern = FOLLOW_PLAY_PATTERN;//打字模式

    public static Boolean progress = true;//进度条
    public static Boolean tip =  true;//词提
    public static Boolean lurk = false;//潜水
    public static Boolean replace = true;//符号替换
    public static Boolean clearSpace = true;//去除空格


    public static Boolean runLogin = false;//自动登录

    public static String username;
    public static String password;


    public static void readConfig(){
        IniAccess.readIni(iniFilePath);
    }
}