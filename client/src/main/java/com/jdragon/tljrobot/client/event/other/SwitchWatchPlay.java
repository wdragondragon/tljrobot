package com.jdragon.tljrobot.client.event.other;

import com.jdragon.tljrobot.client.component.JMenuComponent;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.utils.common.JTextPaneFont;

import javax.swing.*;
import java.awt.*;

/**
 * Create by Jdragon on 2020.02.06
 */
public class SwitchWatchPlay {
    public static void start(){
        LocalConfig.typingPattern = Constant.WATCH_PLAY_PATTERN;
        JTextPaneFont.createStyle("黑", LocalConfig.typeDocName,
                LocalConfig.fontSize, 0, 0, 0, Color.BLACK, LocalConfig.family,
                LocalConfig.watchingBackgroundColor);
        JTextPaneFont.createStyle("红", LocalConfig.typeDocName,
                LocalConfig.fontSize, 0, 0, 0, Color.BLACK, LocalConfig.family,
                LocalConfig.watchingBackgroundColor);
        JOptionPane.showMessageDialog(null,"ctrl+enter提交成绩后，灰色代表打少字，粉色代表打多字，红色背景代表错误原字，蓝色为上屏错字");
        JMenuComponent.getInstance().switchingMode.setText("当前模式："+ LocalConfig.typingPattern);
    }
}
