package com.jdragon.tljrobot.client.event.threadEvent;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Create by Jdragon on 2020.02.06
 */
public class SoundRecord extends Thread{
    static Player player;
    static boolean isStart;
//    static SourceDataLine player;
    static File file;
    static FileInputStream stream = null;
    public SoundRecord(){}
    public SoundRecord(File file){
        this.file = file;
    }
    public void run(){
        try {
            Thread.sleep(3000);
            recordStart();
        }catch (Exception ignore){}
    }
    public static void recordStart(){
        try {
            if(isStart)return;
            isStart = true;
            stream = new FileInputStream(file);
            player = new Player(stream);
            player.play();
            isStart = false;
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
//        try {
//            if(isStart)return;
//            isStart = true;
//            play_mp3(fileName);
//            isStart = false;
//        } catch (UnsupportedAudioFileException | IOException e) {
//            e.printStackTrace();
//        }
    }
    public static void recordStop(){
        if(isStart)
            player.close();
        isStart = false;
    }
//    public static void play_mp3(String path) throws UnsupportedAudioFileException, IOException {
//        File file=new File(path);
//        if(!file.exists() || !path.toLowerCase().endsWith(".mp3")) {
//            throw new RuntimeException("文件不存在");
//        }
//        AudioInputStream stream = null;
//        //使用 mp3spi 解码 mp3 音频文件
//        MpegAudioFileReader mp = new MpegAudioFileReader();
//        stream = mp.getAudioInputStream(file);
//        AudioFormat baseFormat = stream.getFormat();
//        //设定输出格式为pcm格式的音频文件
//        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
//        // 输出到音频
//        stream = AudioSystem.getAudioInputStream(format, stream);
//        AudioFormat target = stream.getFormat();
//        DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, target, AudioSystem.NOT_SPECIFIED);
////        SourceDataLine player = null;
//        int len = -1;
//        try {
//            player = (SourceDataLine) AudioSystem.getLine(dinfo);
//            player.open(target);
//            player.start();
//            byte[] buffer = new byte[1024];
//            while ((len = stream.read(buffer)) > 0) {
//                player.write(buffer, 0, len);
//            }
//            player.drain();
//            player.stop();
//            player.close();
//        }catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        } finally {
//            stream.close();
//        }
//    }
}