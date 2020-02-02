package com.jdragon.tljrobot.client.config;

import com.jdragon.tljrobot.client.utils.common.JTextPaneFont;

import java.awt.*;
import static com.jdragon.tljrobot.client.config.LocalConfig.*;
public class FontColorConfig {
    public static void start(){
        JTextPaneFont.creat(typeDocName);
        JTextPaneFont.createStyle("黑", typeDocName, fontSize,
                0, 0, 0, Color.BLACK, family, rightColor);
        JTextPaneFont.createStyle("红", typeDocName, fontSize,
                0, 0, 0, Color.BLACK, family, mistakeColor);
        JTextPaneFont.createStyle("灰", typeDocName, fontSize,
                0, 0, 0, Color.BLACK, family, mistakeColor);// GRAY

        JTextPaneFont.createStyle("蓝粗", typeDocName, fontSize,
                1, 0, 0, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("蓝", typeDocName, fontSize,
                0, 0, 0, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("蓝斜", typeDocName, fontSize,
                0, 1, 0, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("蓝粗斜", typeDocName, fontSize,
                1, 1, 0, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉粗", typeDocName, fontSize,
                1, 0, 0, twoCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉", typeDocName, fontSize,
                0, 0, 0, twoCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉斜", typeDocName, fontSize,
                0, 1, 0, twoCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉粗斜", typeDocName, fontSize,
                1, 1, 0, twoCodeColor, family, mistakeColor);// GRAY

        JTextPaneFont.createStyle("绿粗", typeDocName, fontSize,
                1, 0, 0, fourCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("绿", typeDocName, fontSize,
                0, 0, 0, fourCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("绿斜", typeDocName, fontSize,
                0, 1, 0, fourCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("绿粗斜", typeDocName, fontSize,
                1, 1, 0, fourCodeColor, family, mistakeColor);// GRAY
    }
}
