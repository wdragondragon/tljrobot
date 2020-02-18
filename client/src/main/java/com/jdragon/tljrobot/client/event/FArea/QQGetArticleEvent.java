package com.jdragon.tljrobot.client.event.FArea;

import com.jdragon.tljrobot.client.utils.common.QqOperation;
import com.jdragon.tljrobot.tljutils.SystemUtil;

import static com.jdragon.tljrobot.client.component.SwingSingleton.qQNameLabel;

public class QQGetArticleEvent {
    public static boolean isGetArticleSign;
    public static void start(){
        try {
            if(SystemUtil.isWindows()){
                QqOperation.start(QqOperation.GET_ARTICLE, qQNameLabel().getText());
                isGetArticleSign = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
