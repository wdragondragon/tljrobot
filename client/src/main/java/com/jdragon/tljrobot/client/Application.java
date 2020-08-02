package com.jdragon.tljrobot.client;

import com.jdragon.tljrobot.client.config.JTextPaneFontConfig;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.config.MainFraConfig;
import com.jdragon.tljrobot.client.config.VersionConfig;
import com.jdragon.tljrobot.client.event.threadEvent.DelayedOperationThread;
import com.jdragon.tljrobot.client.event.threadEvent.DynamicSpeedThread;
import com.jdragon.tljrobot.client.utils.common.DateNumInitThread;
import com.jdragon.tljrobot.client.utils.common.KeyboardHookThread;
import com.jdragon.tljrobot.client.window.MainFra;
import com.jdragon.tljrobot.client.window.dialog.LogonDialog;


public class Application {
    public static void main(String[] args){
        LocalConfig.readConfig();

        VersionConfig.start();

        JTextPaneFontConfig.start();
        MainFraConfig.start();


        MainFra.getInstance().addListener();//添加监听器
        DynamicSpeedThread.getInstance().start();//动态计时


        new DelayedOperationThread().start();//载文线程
        if(LocalConfig.runLogin) {
            LogonDialog.doLogin();
        }
        new DateNumInitThread().start();
        if(LocalConfig.globalReplay) {
            KeyboardHookThread kbhook = new KeyboardHookThread();
            new Thread(kbhook).start();
        }
    }
}
