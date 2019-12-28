package com.jdragon.tljrobot.client;

import com.jdragon.tljrobot.client.config.FontColorConfig;
import com.jdragon.tljrobot.client.config.MainFraConfig;
import com.jdragon.tljrobot.client.window.MainFra;


public class Application {
    public static void main(String[] args){
        FontColorConfig.start();
        MainFraConfig.start();
        MainFra.getInstance().addListener();
    }
}
