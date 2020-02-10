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
            TypingText().setText("");
            WatchingText().setText(""); // 清空文本框
            TypingState.init();//打字状态初始化
            ListenPlayEvent.replay();
        }else {
            TypingText().setText("");
            TypingListener.getInstance().changeFontColor();
            SpeedButton().setText("0.00");
            KeySpeedButton().setText("0.00");
            KeyLengthButton().setText("0.00");
            NumberLabel().setText("字数:" + Article.getArticleSingleton().getArticle().length()
                    + "/已打:0/错:0");
            TypingState.init();//打字状态初始化
            //文本框初始化
            WatchingText().setCaretPosition(0);
            WatchingJSP().getVerticalScrollBar().setValue(0);
            //进度条初始化
            int articleLength = Article.getArticleSingleton().getArticle().length();
            TypingProgress().setMinimum(0);
            TypingProgress().setMaximum(articleLength);
            TypingProgress().setValue(0);

            TypingText().setEditable(true);
            TypingText().requestFocusInWindow();

            if (!TypingState.dailyCompetition)
                TypingListener.getInstance().changeTipLabel(0);//提示第一个字

            TheoreticalCodeLengthButton().setText(String.valueOf(Article.getArticleSingleton().getShortCodeEntity().getArticleAverCodes()));

            TypingState.pause = false;
            TypingText().requestFocusInWindow();
        }
    }
}
