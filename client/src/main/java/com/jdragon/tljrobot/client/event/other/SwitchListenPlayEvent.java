package com.jdragon.tljrobot.client.event.other;

import com.jdragon.tljrobot.client.component.JMenuComponent;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;

/**
 * Create by Jdragon on 2020.02.06
 */
public class SwitchListenPlayEvent {
    public static void start(){
        LocalConfig.typingPattern = Constant.LISTEN_PLAY_PATTERN;
        JMenuComponent.getInstance().switchingMode.setText("当前模式："+ LocalConfig.typingPattern);
    }
}
