package com.jdragon.tljrobot.client.event.threadEvent;

import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.component.SwingSingleton;

/**
 * Create by Jdragon on 2020.01.12
 */
public class DynamicSpeed extends Thread {
    private static DynamicSpeed dynamicSpeed = null;
    public static DynamicSpeed getInstance(){
        if(dynamicSpeed==null)dynamicSpeed = new DynamicSpeed();
        return dynamicSpeed;
    }
    private DynamicSpeed(){}
    public void run() {
        while (true) {
            try {
                sleep(10);
                if (TypingState.typingState&&!TypingState.pause) {//跟打时并没有暂停时才计算
                    TypingState.timer.timeEnd();
                    SwingSingleton.SpeedButton().setText(String.format("%.2f",
                            TypingState.getSpeed()));
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
