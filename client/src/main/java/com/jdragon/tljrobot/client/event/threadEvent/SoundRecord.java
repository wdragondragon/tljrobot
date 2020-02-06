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
    static String fileName;
    public SoundRecord(String fileName){
        this.fileName = fileName;
    }
    public void run(){
        try {
            Thread.sleep(3000);
            recordStart();
        }catch (Exception ignore){}
    }
    public static void recordStart(){
        File file = new File(fileName);
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            player = new Player(stream);
            player.play();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
    public static void recordStop(){
        player.close();
    }
}