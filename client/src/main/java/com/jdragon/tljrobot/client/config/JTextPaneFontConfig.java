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
                    false, false, false, Color.BLACK, family, rightColor);
            JTextPaneFont.createStyle("红",  fontSize,
                    false, false, false, Color.BLACK, family, mistakeColor);
        }else{
            JTextPaneFont.createStyle("黑", LocalConfig.fontSize, false, false, false, Color.BLACK, LocalConfig.family,
                    LocalConfig.watchingBackgroundColor);
            JTextPaneFont.createStyle("红", LocalConfig.fontSize, false, false, false, Color.BLACK, LocalConfig.family,
                    LocalConfig.watchingBackgroundColor);
        }
        JTextPaneFont.createStyle("灰",  fontSize,
                false, false, false, Color.BLACK, family, mistakeColor);// GRAY

        JTextPaneFont.createStyle("蓝粗",  fontSize,
                true, false, false, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("蓝",  fontSize,
                false, false, false, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("蓝斜",  fontSize,
                false, true, false, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("蓝粗斜",  fontSize,
                true, true, false, threeCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉粗",  fontSize,
                true, false, false, twoCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉",  fontSize,
                false, false, false, twoCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉斜",  fontSize,
                false, true, false, twoCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("粉粗斜",  fontSize,
                true, true, false, twoCodeColor, family, mistakeColor);// GRAY

        JTextPaneFont.createStyle("绿粗",  fontSize,
                true, false, false, fourCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("绿",  fontSize,
                false, false, false, fourCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("绿斜",  fontSize,
                false, true, false, fourCodeColor, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("绿粗斜",  fontSize,
                true, true, false, fourCodeColor, family, mistakeColor);// GRAY

        JTextPaneFont.createStyle("对", LocalConfig.fontSize, false, false, false, Color.BLACK, LocalConfig.family,
                LocalConfig.rightColor);
        JTextPaneFont.createStyle("错原", LocalConfig.fontSize, false, false, false, Color.BLACK, LocalConfig.family,
                LocalConfig.mistakeColor);

        JTextPaneFont.createStyle("多",  fontSize,
                true, false, false, Color.pink, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("少",  fontSize,
                false, false, false, Color.gray, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("错",  (int) (fontSize*0.6),
                true, false, true, Color.blue, family, mistakeColor);// GRAY
        JTextPaneFont.createStyle("忽略",  fontSize,
                false, false, false, Color.CYAN, family, rightColor);// GRAY
    }
}
