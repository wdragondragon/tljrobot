package com.jdragon.tljrobot.client.event.threadEvent;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.TypingState;

/**
 * Create by Jdragon on 2020.01.12
 */
public class DynamicSpeedThread extends Thread {
    private static DynamicSpeedThread dynamicSpeedThread = null;
    public static DynamicSpeedThread getInstance(){
        if(dynamicSpeedThread ==null) dynamicSpeedThread = new DynamicSpeedThread();
        return dynamicSpeedThread;
    }
    private DynamicSpeedThread(){}
    public void run() {
        while (true) {
            try {
                sleep(100);
                if (TypingState.typingState&&!TypingState.pause) {//跟打时并没有暂停时才计算
                    TypingState.timer.timeEnd();
                    if(LocalConfig.typingPattern.equals(Constant.FOLLOW_PLAY_PATTERN))
                        SwingSingleton.SpeedButton().setText(String.format("%.2f",
                                TypingState.getSpeed()));
                    else
                        SwingSingleton.SpeedButton().setText(String.format("%.2f",
                                TypingState.getSpeedNoMistake()));
                    SwingSingleton.KeySpeedButton().setText(String.format("%.2f",
                            TypingState.getKeySpeed()));
                    SwingSingleton.KeyLengthButton().setText(String.format("%.2f",
                            TypingState.getKeyLength()));
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
