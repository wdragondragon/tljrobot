package com.jdragon.tljrobot.client.event.FArea;

import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.listener.common.Typing;

import static com.jdragon.tljrobot.client.factory.SwingSingleton.*;

public class Replay {
    public static void start(){
        Typing.getInstance().changeFontColor();
        TypingState.init();//打字状态初始化
        WatchingText().setCaretPosition(0);
        WatchingJSP().getVerticalScrollBar().setValue(0);
    }
}
