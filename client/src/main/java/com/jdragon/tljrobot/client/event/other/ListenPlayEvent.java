package com.jdragon.tljrobot.client.event.other;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.event.FArea.ReplayEvent;
import com.jdragon.tljrobot.client.event.threadEvent.SoundRecordThread;
import com.jdragon.tljrobot.client.utils.common.ChooseFile;
import com.jdragon.tljrobot.client.window.MainFra;
import com.jdragon.tljrobot.tljutils.ArticleUtil;

import javax.swing.*;
import java.io.File;
import java.io.RandomAccessFile;

/**
 * Create by Jdragon on 2020.02.06
 */
public class ListenPlayEvent {
    private static String title;
    private static String content;
    private static int length;
    private static String fileName;
    private static SoundRecordThread soundRecordThread;
    private static boolean isRead = false;
    private static File mp3;
    private static File txt;
    public static void start(){
        if (!LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)) {
            JOptionPane.showMessageDialog(MainFra.getInstance(), "切换成听打再尝试");
            return;
        }
        fileName = ChooseFile.getFileName();
        if(fileName==null) {
            return;
        }
        fileName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf(".")) : null;
        if(fileName==null){
            JOptionPane.showMessageDialog(MainFra.getInstance(),"格式错误");
            return;
        }
        title = fileName.contains("\\") ? fileName.substring(fileName.lastIndexOf("\\")+1) : null;
        if(title==null){
            JOptionPane.showMessageDialog(MainFra.getInstance(),"文件错误");
            return;
        }
        byte[] s;
        try {
            txt = new File(fileName + ".txt");
            RandomAccessFile in = new RandomAccessFile(txt, "r");
            s = new byte[(int) in.length()];
            in.readFully(s);
        }catch (Exception e){
            JOptionPane.showMessageDialog(MainFra.getInstance(),"打开txt失败");
            return;
        }
        content = new String(s);
        content = ArticleUtil.clearSpace(content);
        content = ArticleUtil.replace(content);
        mp3 = new File(fileName+".mp3");
        if(!mp3.exists()){
            JOptionPane.showMessageDialog(MainFra.getInstance(),"打开mp3失败");
            return;
        }
        soundRecordThread = new SoundRecordThread(mp3);
        isRead = true;
        Article.getArticleSingleton(1, title,content);
//        soundRecord.start();
        ReplayEvent.start();
    }
    public static void replay(){
        stop();
        if(!isRead) {
            start();
        } else{
            soundRecordThread = new SoundRecordThread();
            soundRecordThread.start();
        }
    }
    public static void stop(){
        SoundRecordThread.recordStop();
    }
    public static String getContent(){return content;}
    public static String getTitle(){return title;}
    public static int getLength(){return length;}
    public static void setLength(int length){
        ListenPlayEvent.length = length;}
}
