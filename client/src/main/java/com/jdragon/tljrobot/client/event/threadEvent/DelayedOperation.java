package com.jdragon.tljrobot.client.event.threadEvent;

import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.FArea.QQGetArticle;
import com.jdragon.tljrobot.client.event.FArea.Replay;
import com.jdragon.tljrobot.client.event.FArea.SendAchievement;
import com.jdragon.tljrobot.client.event.online.Match;
import com.jdragon.tljrobot.client.listener.common.Typing;
import com.jdragon.tljrobot.client.utils.common.ArticleRegex;
import com.jdragon.tljrobot.client.utils.common.Clipboard;

import javax.swing.*;
import java.util.Objects;


/**
 * Create by Jdragon on 2020.01.13
 */
public class DelayedOperation extends Thread {
    public void run(){
        while (true) {
            try {
                sleep(10);
                if (QQGetArticle.isGetArticleSign){
                    QQGetArticle.isGetArticleSign = false;
                    Article article = ArticleRegex.regexStringToArticle(Objects.requireNonNull(Clipboard.get()));
                    Replay.start();
                }
                if (Typing.delaySendResultSign){
                    Typing.delaySendResultSign = false;
                    sleep(200);
                    TypingState.typingState = false;//跟打结束标志使DynamicSpeed中计算停止
                    SendAchievement.start();
                    Typing.getInstance().changeAllFontColor();
                    if(UserState.loginState) {//联网操作，发送跟打历史或发送0段赛文成绩
                        if (TypingState.dailyCompetition) {
                            JOptionPane.showMessageDialog(null,Match.uploadMatchAch());
                            Article.getArticleSingleton(1, "日赛跟打完毕", "日赛跟打完毕");
                            TypingState.dailyCompetition = false;
                            Replay.start();
                        }else{
                            JOptionPane.showMessageDialog(null,Match.uploadHistory());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Clip错误");
            }
            // System.out.println(wenbenstr);
            // AchievementListener.setClipboardString(AchievementListener.getClipboardString());
        }
    }
}
