package com.jdragon.tljrobot.client.event.FArea;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.utils.common.Clipboard;
import com.jdragon.tljrobot.client.utils.common.QqOperation;
import lombok.SneakyThrows;

import javax.swing.*;

public class ShareArticle {
    @SneakyThrows
    public static void start(){
        if(LocalConfig.lurk)return;
        if(TypingState.dailyCompetition){
            JOptionPane.showMessageDialog(null,"先结束日赛");
            return;
        }
        Article article = Article.getArticleSingleton();
        String share = article.getTitle() +
                "\n"+article.getArticle() +
                "\n-----"+"第"+ article.getParagraph() +"段";
        Clipboard.set(share);
        QqOperation.start(QqOperation.SEND_ACHIEVEMENT, SwingSingleton.QQNameLabel().getText());
    }
}
