package com.jdragon.tljrobot.client.event.other;

import com.jdragon.tljrobot.client.component.JMenuComponent;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.event.FArea.ReplayEvent;
import com.jdragon.tljrobot.client.utils.common.JTextPaneFont;

import java.awt.*;

/**
 * Create by Jdragon on 2false2false.false2.false6
 */
public class SwitchFollowPlayEvent {
    public static void start(){
        ListenPlayEvent.stop();

        LocalConfig.typingPattern = Constant.FOLLOW_PLAY_PATTERN;
        JTextPaneFont.createStyle("黑", 
                LocalConfig.fontSize, false, false, false, Color.BLACK, LocalConfig.family,
                LocalConfig.rightColor);
        JTextPaneFont.createStyle("红", 
                LocalConfig.fontSize, false, false, false, Color.BLACK, LocalConfig.family,
                LocalConfig.mistakeColor);
        JMenuComponent.getInstance().switchingMode.setText("当前模式："+ LocalConfig.typingPattern);
        JMenuComponent.getInstance().getMenu().setText(LocalConfig.typingPattern);
        ReplayEvent.start();
    }
}
