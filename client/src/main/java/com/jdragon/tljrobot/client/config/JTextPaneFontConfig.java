package com.jdragon.tljrobot.client.config;

import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.utils.common.JTextPaneFont;

import java.awt.*;
import static com.jdragon.tljrobot.client.config.LocalConfig.*;
public class JTextPaneFontConfig {
    public static void start(){
//        JTextPaneFont.creat(typeDocName);
        if(LocalConfig.typingPattern.equals(Constant.FOLLOW_PLAY_PATTERN)) {
            JTextPaneFont.createStyle("黑" ,fontSize,
                    0, 0, 0, Color.BLACK, family, rightColor);
            JTextPaneFont.createStyle("红",  fontSize,
                    0, 0, 0, Color.BLACK, family, mistakeColor);
        }else{
            JTextPaneFont.createStyle("黑", LocalConfig.fontSize, 0, 0, 0, Color.BLACK, LocalConfig.family,
                    LocalConfig.watchingBackgroundColor);
            JTextPaneFont.createStyle("红", LocalConfig.fontSize, 0, 0, 0, Color.BLACK, LocalConfig.family,
                    LocalConfig.watchingBackgroundColor);
        }
        JTextPaneFont.createStyle("灰",  fontSize,
                0, 0, 0, Color.BLACK, family, mistakeColor);// GRAY

        JTextPaneFont.createStyle("蓝粗",  fontSize,
                1, 0, 0, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("蓝",  fontSize,
                0, 0, 0, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("蓝斜",  fontSize,
                0, 1, 0, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("蓝粗斜",  fontSize,
                1, 1, 0, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉粗",  fontSize,
                1, 0, 0, twoCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉",  fontSize,
                0, 0, 0, twoCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉斜",  fontSize,
                0, 1, 0, twoCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉粗斜",  fontSize,
                1, 1, 0, twoCodeColor, family, mistakeColor);// GRAY

        JTextPaneFont.createStyle("绿粗",  fontSize,
                1, 0, 0, fourCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("绿",  fontSize,
                0, 0, 0, fourCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("绿斜",  fontSize,
                0, 1, 0, fourCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("绿粗斜",  fontSize,
                1, 1, 0, fourCodeColor, family, mistakeColor);// GRAY

        JTextPaneFont.createStyle("对", LocalConfig.fontSize, 0, 0, 0, Color.BLACK, LocalConfig.family,
                LocalConfig.rightColor);
        JTextPaneFont.createStyle("错原", LocalConfig.fontSize, 0, 0, 0, Color.BLACK, LocalConfig.family,
                LocalConfig.mistakeColor);

        JTextPaneFont.createStyle("多",  fontSize,
                1, 0, 0, Color.pink, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("少",  fontSize,
                0, 0, 0, Color.gray, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("错",  (int) (fontSize*0.6),
                1, 0, 1, Color.blue, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("忽略",  fontSize,
                0, 0, 0, Color.CYAN, family, rightColor);// GRAY
    }
}
