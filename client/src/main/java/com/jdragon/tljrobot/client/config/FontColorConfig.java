package com.jdragon.tljrobot.client.config;

import com.jdragon.tljrobot.client.utils.common.JTextPaneFont;

import java.awt.*;

public class FontColorConfig {
    private static Color rightColor = Color.gray;
    private static Color mistakeColor = Color.red;
    private static Color threeCodeColor = Color.BLUE;
    private static Color twoCodeColor = Color.ORANGE;
    private static Color fourCodeColor = new Color(128, 138, 135);
    private static String family = "微软雅黑";

    static public int fontSize = 30;
    public static void start(){
        JTextPaneFont.creat();
        JTextPaneFont.createStyle("黑", JTextPaneFont.styledDoc, fontSize,
                0, 0, 0, Color.BLACK, family, rightColor);
        JTextPaneFont.createStyle("红", JTextPaneFont.styledDoc, fontSize,
                0, 0, 0, Color.BLACK, family, mistakeColor);
        JTextPaneFont.createStyle("灰", JTextPaneFont.styledDoc, fontSize,
                0, 0, 0, Color.BLACK, family, mistakeColor);// GRAY

        JTextPaneFont.createStyle("蓝粗", JTextPaneFont.styledDoc, fontSize,
                1, 0, 0, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("蓝", JTextPaneFont.styledDoc, fontSize,
                0, 0, 0, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("蓝斜", JTextPaneFont.styledDoc, fontSize,
                0, 1, 0, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("蓝粗斜", JTextPaneFont.styledDoc, fontSize,
                1, 1, 0, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉粗", JTextPaneFont.styledDoc, fontSize,
                1, 0, 0, twoCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉", JTextPaneFont.styledDoc, fontSize,
                0, 0, 0, twoCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉斜", JTextPaneFont.styledDoc, fontSize,
                0, 1, 0, twoCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉粗斜", JTextPaneFont.styledDoc, fontSize,
                1, 1, 0, twoCodeColor, family, mistakeColor);// GRAY

        JTextPaneFont.createStyle("绿粗", JTextPaneFont.styledDoc, fontSize,
                1, 0, 0, fourCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("绿", JTextPaneFont.styledDoc, fontSize,
                0, 0, 0, fourCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("绿斜", JTextPaneFont.styledDoc, fontSize,
                0, 1, 0, fourCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("绿粗斜", JTextPaneFont.styledDoc, fontSize,
                1, 1, 0, fourCodeColor, family, mistakeColor);// GRAY
    }
}
