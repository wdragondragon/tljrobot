package com.jdragon.tljrobot.client.utils.common;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.tljutils.compShortCode.BetterTyping;
public class BetterTypingSingleton {
    private static BetterTyping bestTyping = new BetterTyping(bestTypingName = LocalConfig.codeTable);
    private static String bestTypingName;
    public static BetterTyping getInstance(){
        return bestTyping;
    }
    public static void setBetterTyping(String fileName){
        bestTyping = new BetterTyping(bestTypingName = LocalConfig.codeTable = fileName);
    }
    public static String getBestTypingName(){
        return bestTypingName;
    }
    private BetterTypingSingleton(){}
}
