package com.jdragon.tljrobot.client;

import com.jdragon.tljrobot.client.config.FontColorConfig;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.config.LogConfig;
import com.jdragon.tljrobot.client.config.MainFraConfig;
import com.jdragon.tljrobot.client.event.threadEvent.DelayedOperation;
import com.jdragon.tljrobot.client.event.threadEvent.DynamicSpeed;
import com.jdragon.tljrobot.client.utils.common.DateNumInit;
import com.jdragon.tljrobot.client.window.LogonDialog;
import com.jdragon.tljrobot.client.window.MainFra;


public class Application {
    public static void main(String[] args){
        LocalConfig.readConfig();

        LogConfig.start();

        FontColorConfig.start();
        MainFraConfig.start();


        MainFra.getInstance().addListener();//添加监听器
        DynamicSpeed.getInstance().start();//动态计时


        new DelayedOperation().start();//载文线程
        if(LocalConfig.runLogin) LogonDialog.doLogin();
        new DateNumInit().start();
    }
}
