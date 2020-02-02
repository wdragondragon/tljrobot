package com.jdragon.tljrobot.client.event.online;

import com.jdragon.tljrobot.client.config.OnlineConfig;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.tljutils.HttpUtil;

/**
 * Create by Jdragon on 2020.02.02
 */
public class KeepALiveThread extends Thread{
    public void run(){
        while(UserState.loginState) {
            try {
                Thread.sleep(30*60*1000);
                HttpUtil.doPost(OnlineConfig.ME_KEEP_A_LIVE_ADDR,UserState.token);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
