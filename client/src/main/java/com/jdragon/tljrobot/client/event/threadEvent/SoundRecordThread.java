package com.jdragon.tljrobot.client.event.threadEvent;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.listener.common.TypingListener;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.jdragon.tljrobot.client.component.SwingSingleton.typingText;

/**
 * Create by Jdragon on 2020.02.06
 */
public class SoundRecordThread extends Thread {
    static AdvancedPlayer player;
    static boolean isStart;
    static File file;
    static FileInputStream stream = null;

    public SoundRecordThread() {
    }

    public SoundRecordThread(File file) {
        SoundRecordThread.file = file;
    }

    public void run() {
        try {
            Thread.sleep(3000);
            if (LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN))
                recordStart();
        } catch (Exception ignore) {
        }
    }

    public static void recordStart() {
        try {
            if (isStart) return;
            isStart = true;
            stream = new FileInputStream(file);
            player = new AdvancedPlayer(stream);
            player.play();
            try {
                int timeInSecond = 180;
                while (timeInSecond > 0 && isStart) {
                    Thread.sleep(1000);
                    timeInSecond--;
                    SwingSingleton.watchingText().setText("录音播放完毕，剩余" + timeInSecond + "秒自动提交成绩");
                }
            } catch (InterruptedException e) {
                System.err.println("倒计时线程被中断: " + e.getMessage());
            }
            isStart = false;
            typingText().setEditable(false); // 设置不可打字状态
            TypingListener.delaySendResultSign = true;
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public static void recordStop() {
        if (isStart)
            player.close();
        isStart = false;
    }
}