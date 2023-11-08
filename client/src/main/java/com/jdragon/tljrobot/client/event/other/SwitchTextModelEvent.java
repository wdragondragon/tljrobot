package com.jdragon.tljrobot.client.event.other;

import com.jdragon.tljrobot.client.component.JMenuComponent;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;

/**
 * @author JDragon
 * @date 2023/11/8 12:05
 * @description
 */
public class SwitchTextModelEvent {
    public static void start(Integer textMode) {
        LocalConfig.textMode = textMode;
        JMenuComponent.getInstance().switchTextMode.setText("当前模式：" + (LocalConfig.textMode == Constant.TEXT_MODE_CN ? "中文" : "英文"));
        if (LocalConfig.textMode == Constant.TEXT_MODE_EN) {
            SwitchFollowPlayEvent.start();
        }
    }
}
