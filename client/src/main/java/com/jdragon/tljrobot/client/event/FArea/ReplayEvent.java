package com.jdragon.tljrobot.client.event.FArea;

import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.event.other.ListenPlayEvent;
import com.jdragon.tljrobot.client.listener.common.TypingListener;

import javax.swing.*;

import static com.jdragon.tljrobot.client.component.SwingSingleton.*;

public class ReplayEvent {
    public static void start(){
        if(TypingState.dailyCompetition){
            JOptionPane.showMessageDialog(null,"先结束日赛");
            return;
        }
        if(LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)){
            typingText().setText("");
            watchingText().setText(""); // 清空文本框
            TypingState.init();//打字状态初始化
            ListenPlayEvent.replay();
        }else {
            typingText().setText("");
            TypingListener.getInstance().changeFontColor();
            speedButton().setText("0.00");
            keySpeedButton().setText("0.00");
            keyLengthButton().setText("0.00");
            numberLabel().setText("字数:" + Article.getArticleSingleton().getArticle().length()
                    + "/已打:0/错:0");
            TypingState.init();//打字状态初始化
            //文本框初始化
            watchingText().setCaretPosition(0);
            watchingJsp().getVerticalScrollBar().setValue(0);
            //进度条初始化
            int articleLength = Article.getArticleSingleton().getArticle().length();
            typingProgress().setMinimum(0);
            typingProgress().setMaximum(articleLength);
            typingProgress().setValue(0);

            typingText().setEditable(true);
            typingText().requestFocusInWindow();

            if (!TypingState.dailyCompetition) {
                TypingListener.getInstance().changeTipLabel(0);//提示第一个字
            }

            theoreticalCodeLengthButton().setText(String.valueOf(Article.getArticleSingleton().getShortCodeEntity().getArticleAverCodes()));

            TypingState.pause = false;
            typingText().requestFocusInWindow();
        }
    }
}
