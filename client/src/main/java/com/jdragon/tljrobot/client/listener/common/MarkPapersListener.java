package com.jdragon.tljrobot.client.listener.common;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.jdragon.tljrobot.client.component.SwingSingleton.typingText;

public class MarkPapersListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        action();
    }

    public static void action() {
        if (LocalConfig.typingPattern.equals(Constant.WATCH_PLAY_PATTERN)) {
            if (SwingSingleton.typingText().getText().isEmpty()) {
                return;
            }
            typingText().setEditable(false); // 设置不可打字状态
            TypingListener.delaySendResultSign = true;
        } else if (LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)) {
            typingText().setEditable(false); // 设置不可打字状态
            TypingListener.delaySendResultSign = true;
        }
    }
}
