package com.jdragon.tljrobot.client.factory;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.tljutils.compShortCode.BetterTyping;
public class BetterTypingSingleton {
    private static BetterTyping bestTyping = null;
    private static String bestTypingName;
    public static BetterTyping getInstance(){
        if(bestTyping==null){
            bestTyping = new BetterTyping(bestTypingName = LocalConfig.codeTable);
        }
        return bestTyping;
    }
    public static String getBestTypingName(){
        return bestTypingName;
    }
    private BetterTypingSingleton(){};
}
