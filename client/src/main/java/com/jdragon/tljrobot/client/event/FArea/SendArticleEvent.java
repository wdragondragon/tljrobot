package com.jdragon.tljrobot.client.event.FArea;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.listener.common.ArticleTreeListener;
import com.jdragon.tljrobot.client.window.MainFra;
import com.jdragon.tljrobot.client.window.dialog.SendArticleDialog;

import javax.swing.*;

/**
 * Create by Jdragon on 2020.01.31
 */
public class SendArticleEvent {
    public static void start(){
        if(TypingState.dailyCompetition){
            JOptionPane.showMessageDialog(MainFra.getInstance(),"请先结束每日赛文");return;}
        if (TypingState.sendArticle!=0){
            int n = JOptionPane.showConfirmDialog(MainFra.getInstance(), "正在发文，要取消发文吗?", "正在发文提示", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                // ......
                ArticleTreeListener.fontweizhi = 0;
                SwingSingleton.sendArticleLabel().setVisible(false);
                TypingState.sendArticle = 0;
            }  // ......

        }
        else {
            SendArticleDialog.getInstance().setVisible(true);
        }
    }
}
