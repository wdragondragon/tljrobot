package com.jdragon.tljrobot.client.event.threadEvent;

import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.event.FArea.QQGetArticle;
import com.jdragon.tljrobot.client.event.FArea.Replay;
import com.jdragon.tljrobot.client.event.FArea.SendAchievement;
import com.jdragon.tljrobot.client.event.online.Match;
import com.jdragon.tljrobot.client.listener.common.Typing;
import com.jdragon.tljrobot.client.utils.common.ArticleRegex;
import com.jdragon.tljrobot.client.utils.common.Clipboard;

import java.util.Objects;

import static com.jdragon.tljrobot.client.component.SwingSingleton.*;
import static com.jdragon.tljrobot.client.entry.TypingState.dailyCompetition;
import static com.jdragon.tljrobot.client.entry.TypingState.typingState;

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
                    sleep(200);
                    typingState = false;//跟打结束标志
                    TypingState.timer.timeEnd();
                    SpeedButton().setText(""+TypingState.getSpeed());
                    KeySpeedButton().setText(""+TypingState.getKeySpeed());
                    KeyLengthButton().setText(""+TypingState.getKeyLength());
                    SendAchievement.start();
                    Typing.getInstance().changeAllFontColor();
                    Typing.delaySendResultSign = false;
                    if(dailyCompetition){
                        Match.uploadAch();
                        Article.getArticleSingleton(1,"日赛跟打完毕","日赛跟打完毕");
                        dailyCompetition = false;
                        Replay.start();
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
