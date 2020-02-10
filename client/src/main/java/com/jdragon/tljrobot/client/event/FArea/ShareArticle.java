package com.jdragon.tljrobot.client.event.FArea;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.listener.common.ArticleTreeListener;
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
                System.lineSeparator()+article.getArticle() +
                System.lineSeparator()+"-----"+"第"+ article.getParagraph() +"段";
        if(TypingState.sendArticle== Constant.SEND_ORDER){
            share += "-余"+(ArticleTreeListener.length-ArticleTreeListener.fontweizhi)+"字";
        }
        Clipboard.set(share);
        QqOperation.start(QqOperation.SEND_ACHIEVEMENT, SwingSingleton.QQNameLabel().getText());
    }
}
