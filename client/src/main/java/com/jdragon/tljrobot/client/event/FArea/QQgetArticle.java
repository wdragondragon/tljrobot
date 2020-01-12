package com.jdragon.tljrobot.client.event.FArea;

import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.utils.common.ArticleRegex;
import com.jdragon.tljrobot.client.utils.common.Clipboard;
import com.jdragon.tljrobot.client.utils.common.QqOperation;

import static com.jdragon.tljrobot.client.factory.SwingSingleton.QQNameLabel;

public class QQgetArticle {
    public static void start(){
        try {
            QqOperation.start(QqOperation.GET_ARTICLE,QQNameLabel().getText());
            Article article = ArticleRegex.regexStringToArticle(Clipboard.get());
            Replay.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
