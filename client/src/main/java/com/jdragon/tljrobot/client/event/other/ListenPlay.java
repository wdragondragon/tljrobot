package com.jdragon.tljrobot.client.event.other;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.event.threadEvent.SoundRecord;
import com.jdragon.tljrobot.client.utils.common.ChooseFile;
import com.jdragon.tljrobot.client.window.MainFra;
import com.jdragon.tljrobot.tljutils.ArticleUtil;
import lombok.SneakyThrows;

import javax.swing.*;
import java.io.File;
import java.io.RandomAccessFile;

/**
 * Create by Jdragon on 2020.02.06
 */
public class ListenPlay {
    private static String title;
    private static String content;
    private static String fileName;
    private static SoundRecord soundRecord;
    private static boolean isRead = false;
    private static boolean isStart = false;
    @SneakyThrows
    public static void start(){
        if (!LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)) {
            JOptionPane.showMessageDialog(MainFra.getInstance(), "切换成听打再尝试");
            return;
        }
        fileName = ChooseFile.getFileName();
        fileName = fileName.substring(0,fileName.lastIndexOf("."));
        title = fileName.substring(fileName.lastIndexOf("\\")+1);
        System.out.println(title);
        isRead = true;
        File open = new File(fileName+".txt");
        RandomAccessFile in = new RandomAccessFile(open, "r");
        byte[] s = new byte[(int)in.length()];
        in.readFully(s);
        content = new String(s);
        content = ArticleUtil.clearSpace(content);
        content = ArticleUtil.replace(content);
        soundRecord = new SoundRecord(fileName+".mp3");
        soundRecord.start();
        isStart = true;
    }
    public static void replay(){
        stop();
        if(!isRead)start();
        else {
            soundRecord = new SoundRecord(fileName + ".mp3");
            soundRecord.start();
            isStart = true;
        }
    }
    public static void stop(){
        if(isStart) {
            SoundRecord.recordStop();
            isStart = false;
        }
    }
    public static String getContent(){return content;}
    public static String getTitle(){return title;}
}
