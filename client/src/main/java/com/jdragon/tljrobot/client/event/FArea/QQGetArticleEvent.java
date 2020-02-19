package com.jdragon.tljrobot.client.event.FArea;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.config.LocalSystem;
import com.jdragon.tljrobot.client.utils.common.NetArticleTools;
import com.jdragon.tljrobot.client.utils.common.QqOperation;

import static com.jdragon.tljrobot.client.component.SwingSingleton.qQNameLabel;

public class QQGetArticleEvent {
    public static boolean isGetArticleSign;
    public static void start(){
        try {
            if(LocalSystem.isWindows()&&!LocalConfig.getArticleOnNet) {
                QqOperation.start(QqOperation.GET_ARTICLE, qQNameLabel().getText());
                isGetArticleSign = true;
            }else if(!LocalSystem.isWindows()||LocalConfig.getArticleOnNet){
                if(NetArticleTools.getArticle()){
                    ReplayEvent.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
