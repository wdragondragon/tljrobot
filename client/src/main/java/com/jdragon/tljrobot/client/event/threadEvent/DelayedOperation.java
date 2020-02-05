package com.jdragon.tljrobot.client.event.threadEvent;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.FArea.QQGetArticle;
import com.jdragon.tljrobot.client.event.FArea.Replay;
import com.jdragon.tljrobot.client.event.FArea.SendAchievement;
import com.jdragon.tljrobot.client.event.online.HistoryEvent;
import com.jdragon.tljrobot.client.listener.common.ArticleTreeListener;
import com.jdragon.tljrobot.client.listener.common.MixListener;
import com.jdragon.tljrobot.client.listener.common.Typing;
import com.jdragon.tljrobot.client.utils.common.ArticleRegex;
import com.jdragon.tljrobot.client.utils.common.Clipboard;
import com.jdragon.tljrobot.client.window.SendArticleDialog;
import com.jdragon.tljrobot.tljutils.string.Comparison;

import java.util.HashMap;
import java.util.List;
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
                    ArticleRegex.regexStringToArticle(Objects.requireNonNull(Clipboard.get()));
                    Replay.start();
                }
                if (Typing.delaySendResultSign){
                    Typing.delaySendResultSign = false;
                    if(LocalConfig.typingPattern.equals(Constant.FOLLOW_PLAY_PATTERN))
                        Typing.getInstance().changeAllFontColor();
                    else{
                        List<HashMap<String,Integer>> hashMapList =
                                Comparison.getComparisonResult(Article.getArticleSingleton().getArticle(), SwingSingleton.TypingText().getText());
                        Typing.getInstance().changeLookPlayFontColor(hashMapList);
                        SwingSingleton.SpeedButton().setText(String.format("%.2f",
                                TypingState.getSpeed()));
                    }

                    sleep(200);
                    TypingState.typingState = false;//跟打结束标志使DynamicSpeed中计算停止
                    SendAchievement.start();

                    if(UserState.loginState) {//联网操作，发送跟打历史或发送0段赛文成绩
                        if (TypingState.dailyCompetition) {
                            HistoryEvent.uploadMatchAch();
                            Article.getArticleSingleton(1, "日赛跟打完毕", "日赛跟打完毕");
                            TypingState.dailyCompetition = false;
                            Replay.start();
                        }else{
                           HistoryEvent.uploadHistory();
                           if(TypingState.sendArticle==0)continue;
                            //自动下一段判断
                            double nextSpeed = Double.parseDouble(String.valueOf(SendArticleDialog.spinnerSpeed.getValue()));
                            double nextKey = Double.parseDouble(String.valueOf(SendArticleDialog.spinnerKey.getValue()));
                            double nextKeyAccuracy = Double.parseDouble(String.valueOf(SendArticleDialog.spinnerKeyLength.getValue()));
                            double speed = TypingState.getSpeed();
                            double keySpeed = TypingState.getKeySpeed();
                            double keyAccuracy = TypingState.getKeyAccuracy();
                            if(TypingState.sendArticle!=0) {
                                if (!(nextSpeed == 0 && nextKey == 0 && nextKeyAccuracy == 0)
                                        && (nextSpeed == 0 || speed >= nextSpeed)
                                        && (nextKey == 0 || keySpeed >= nextKey)
                                        && (nextKeyAccuracy == 0 || keyAccuracy >= nextKeyAccuracy)
                                ) {
                                    if (TypingState.sendArticle == Constant.SEND_EXTRACT) {
                                        ArticleTreeListener.getInstance().chouqu("下一段");
                                    } else if (TypingState.sendArticle == Constant.SEND_ORDER) {
                                        ArticleTreeListener.getInstance().nextOrder();
                                    } else if (TypingState.sendArticle == Constant.SEND_WORDS) {
                                        ArticleTreeListener.getInstance().ciKuNext();
                                    }
                                } else if (!(nextSpeed == 0 && nextKey == 0 && nextKeyAccuracy == 0)) {
                                    String caoZuo = SendArticleDialog.caozuo.getSelectedItem().toString();
                                    switch (caoZuo) {
                                        case "不操作":
                                            break;
                                        case "乱序":
                                            MixListener.getInstance().mixButton("该段乱序");
                                            break;
                                        case "重打":
                                            Replay.start();
                                            break;
                                    }
                                }
                            }
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
