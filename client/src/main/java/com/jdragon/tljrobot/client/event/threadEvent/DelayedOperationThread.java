package com.jdragon.tljrobot.client.event.threadEvent;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.*;
import com.jdragon.tljrobot.client.event.FArea.QQGetArticleEvent;
import com.jdragon.tljrobot.client.event.FArea.ReplayEvent;
import com.jdragon.tljrobot.client.event.FArea.SendAchievementEvent;
import com.jdragon.tljrobot.client.event.online.UploadHistory;
import com.jdragon.tljrobot.client.event.online.UploadMatchAch;
import com.jdragon.tljrobot.client.event.other.ListenPlayEvent;
import com.jdragon.tljrobot.client.listener.common.ArticleTreeListener;
import com.jdragon.tljrobot.client.listener.common.MixListener;
import com.jdragon.tljrobot.client.listener.common.TypingListener;
import com.jdragon.tljrobot.client.utils.common.*;
import com.jdragon.tljrobot.client.window.dialog.SendArticleDialog;
import com.jdragon.tljrobot.client.window.dialog.ThisHistoryDialog;
import com.jdragon.tljrobot.tljutils.ArticleUtil;
import com.jdragon.tljrobot.tljutils.string.Comparison;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.jdragon.tljrobot.client.component.SwingSingleton.typingText;


/**
 * Create by Jdragon on 2020.01.13
 */
public class DelayedOperationThread extends Thread {
    @Override
    public void run(){
        while (true) {
            try {
                sleep(10);
                if (QQGetArticleEvent.isGetArticleSign){
                    QQGetArticleEvent.isGetArticleSign = false;
                    ArticleRegex.regexStringToArticle(Objects.requireNonNull(Clipboard.get()));
                    ReplayEvent.start();
                }
                if (TypingListener.delaySendResultSign){
                    List<HashMap<String,Integer>> hashMapList = null;
                    TypingListener.delaySendResultSign = false;
                    switch (LocalConfig.typingPattern) {
                        case Constant.FOLLOW_PLAY_PATTERN:
                            TypingListener.getInstance().changeAllFontColor();
                            break;
                        case Constant.WATCH_PLAY_PATTERN:
//                        hashMapList = Comparison.getComparisonResult(Article.getArticleSingleton().getArticle(),
//                                SwingSingleton.typingText().getText());
//                        TypingListener.getInstance().changeLookPlayFontColor(hashMapList);
                            hashMapList =
                                    Comparison.getComparisonListenResult(Article.getArticleSingleton().getArticle(),
                                            ArticleUtil.clearSpace(typingText().getText()), BetterTypingSingleton.getInstance().getSymbolCode());
                            TypingListener.getInstance().changeListenPlayFontColor(hashMapList);
                            SwingSingleton.speedButton().setText(String.format("%.2f",
                                    TypingState.getSpeed()));
                            break;
                        case Constant.LISTEN_PLAY_PATTERN:
                            hashMapList =
                                    Comparison.getComparisonListenResult(ListenPlayEvent.getContent(),
                                            ArticleUtil.clearSpace(typingText().getText()), BetterTypingSingleton.getInstance().getSymbolCode());
                            TypingListener.getInstance().changeListenPlayFontColor(hashMapList);
                            ListenPlayEvent.stop();
                            break;
                    }
                    typingText().setEditable(false); // 设置不可打字状态
                    sleep(200);
                    TypingState.typingState = false;//跟打结束标志使DynamicSpeed中计算停止
                    ThisHistoryDialog.addRow();
                    SendAchievementEvent.start();
                    if(UserState.loginState) {//联网操作，发送跟打历史或发送0段赛文成绩
                        if (TypingState.dailyCompetition) {
                            new Synchronous(new UploadMatchAch(HistoryUtil.getHistoryEntry())).start();
                            Article.getArticleSingleton(1, "日赛跟打完毕", "日赛跟打完毕");
                            TypingState.dailyCompetition = false;
                            ReplayEvent.start();
                        }else{
                            History history = HistoryUtil.getHistoryEntry();
                            ArticleDto articleDto = new ArticleDto(0, Article.getArticleSingleton().getTitle(),Article.getArticleSingleton().getArticle());
                            HistoryDto historyDto = new HistoryDto(articleDto,history);
                            new Synchronous(new UploadHistory(historyDto)).start();
                        }
                    }
                    if(LocalConfig.typingPattern.equals(Constant.WATCH_PLAY_PATTERN)){
                        DrawUnLookPlayResult.drawUnFollowPlayResultImg(Article.getArticleSingleton().getTitle(), hashMapList,"看打");
                    }else if (LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)){
                        DrawUnLookPlayResult.drawUnFollowPlayResultImg(ListenPlayEvent.getTitle(), hashMapList, "听打");
                    }
                    //自动下一段判断
                    if(TypingState.sendArticle!=0) {
                        double nextSpeed = Double.parseDouble(String.valueOf(SendArticleDialog.spinnerSpeed.getValue()));
                        double nextKey = Double.parseDouble(String.valueOf(SendArticleDialog.spinnerKey.getValue()));
                        double nextKeyAccuracy = Double.parseDouble(String.valueOf(SendArticleDialog.spinnerKeyLength.getValue()));
                        double speed = TypingState.getSpeed();
                        double keySpeed = TypingState.getKeySpeed();
                        double keyAccuracy = TypingState.getKeyAccuracy();
                        if (SendArticleDialog.automatic.isSelected()&& (nextSpeed == 0 || speed >= nextSpeed)
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
                        } else if (SendArticleDialog.automatic.isSelected()) {
                            String caoZuo = SendArticleDialog.caozuo.getSelectedItem().toString();
                            switch (caoZuo) {
                                case "不操作":
                                    break;
                                case "乱序":
                                    MixListener.getInstance().mixButton("该段乱序");
                                    break;
                                case "重打":
                                    ReplayEvent.start();
                                    break;
                                default:break;
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
