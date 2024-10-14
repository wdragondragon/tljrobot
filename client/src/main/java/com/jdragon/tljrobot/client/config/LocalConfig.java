package com.jdragon.tljrobot.client.config;

import com.jdragon.tljrobot.client.utils.core.IniAccess;

import java.awt.*;

import static com.jdragon.tljrobot.client.constant.Constant.CODE_TABLE;
import static com.jdragon.tljrobot.client.constant.Constant.FOLLOW_PLAY_PATTERN;

public class LocalConfig {
    public static String iniFilePath = "../set/config.ini";

    public static String configSection = "config";

    public static String typeDocName = "typeDoc";


    public static Integer windowX = 100;//窗口位置
    public static Integer windowY = 100;//窗口位置
    public static Integer windowWidth = 710;//窗口大小
    public static Integer windowHeight = 520;//窗口大小

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
    public static Integer readyFont = 0;//预读字数

    public static String codeTable = CODE_TABLE;//码表
    public static String typingPattern = FOLLOW_PLAY_PATTERN;//打字模式

    public static Boolean progress = true;//进度条
    public static Boolean tip = true;//词提
    public static Boolean lurk = false;//潜水
    public static Boolean replace = true;//符号替换
    public static Boolean clearSpace = true;//去除空格


    public static Boolean runLogin = false;//自动登录

    public static String username;//自动登录保存的账号
    public static String password;//自动登录保存的密码

    public static String personalTag = "个性签名";
    public static String typeWriting = "输入法";

    public static Boolean shortCodesNum = true;
    public static Boolean articleLength = true;
    public static Boolean deleteTextNumber = true;
    public static Boolean deleteNumber = true;
    public static Boolean mistake = true;
    public static Boolean keyNumber = true;
    public static Boolean repeat = true;
    public static Boolean keyAccuracy = true;
    public static Boolean keyMethod = true;
    public static Boolean wordRate = true;
    public static Boolean repeatRate = true;
    public static Boolean changLiuVersion = true;
    public static Boolean systemVersion = true;
    public static Boolean checkCode = true;
    public static Boolean useTime = true;
    public static Boolean personalTagSign = false;
    public static Boolean typeWritingSign = false;
    public static Boolean ctrlSendAchToQQ = false;

    public static Boolean getArticleOnNet = false;
    public static Boolean mouseGetArticle = true;
    public static Boolean requestFocusInWindow = false;

    public static Boolean globalReplay = false;//全局F3
    public static Boolean ntqqGetArticle = false;//新版qq

    public static Boolean errorPunishment = true;
    public static Boolean morePunishment = true;

    public static Float windowsOpacity = 1.0f;
    public static String windowsTheme = "长流默认";

    public static Boolean undecorated = false;

    public static String regex = "23456789";

    public static Integer localNum = 0;
    public static Integer localRightNum = 0;
    public static Integer localMisNum = 0;
    public static Integer localDateNum = 0;

    public static Boolean quotationMarkReplacement = false;//中文引号替换成英文引号

    public static Integer textMode = 1;

    //    public static String serviceAddr = FinalConfig.TLJ_ADDR;
    public static void readConfig() {
        IniAccess.readIni(iniFilePath);
    }
}