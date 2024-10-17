package com.jdragon.tljrobot.client.event.FArea;


import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.FinalConfig;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.*;
import com.jdragon.tljrobot.client.event.other.ListenPlayEvent;
import com.jdragon.tljrobot.client.listener.common.TypingListener;
import com.jdragon.tljrobot.client.utils.common.Clipboard;
import com.jdragon.tljrobot.client.utils.common.DoCheck;
import com.jdragon.tljrobot.client.utils.common.NetArticleTools;
import com.jdragon.tljrobot.client.utils.common.QqOperation;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import com.jdragon.tljrobot.tljutils.SystemUtil;
import lombok.SneakyThrows;

import static com.jdragon.tljrobot.client.component.SwingSingleton.qQNameLabel;
import static com.jdragon.tljrobot.client.component.SwingSingleton.speedButton;
import static com.jdragon.tljrobot.client.entry.TypingState.*;

public class SendAchievementEvent {
    @SneakyThrows
    public static void start() {
        if (typingState) {
            return;
        }
        System.out.println(SwingSingleton.typingText().getText().length());
        String result;
        if (LocalConfig.typingPattern.equals(Constant.LISTEN_PLAY_PATTERN)) {
            int length = ListenPlayEvent.getLength();
            result = ListenPlayEvent.getTitle() +
                    " 文章长度" + length + " 正确率" + String.format("%.2f", ((double) correct) / length * 100) + "%" +
                    " 听打模式" + " 错:" + lookMis + " 多:" + lookMore + " 少:" + lookMiss +
                    (LocalConfig.useTime ? " 用时" + timer.formatSeconds() : "") +
                    (LocalConfig.changLiuVersion ? " 长流" + FinalConfig.VERSION : "") +
                    (LocalConfig.systemVersion ? " " + SystemUtil.getSystemName() + "版" : "");
            int typeLength = length - lookMiss + lookMore;
            int typeMistake = lookMis + lookMore;
            NumState.num += typeLength;
            NumState.dateNum += typeLength;
            NumState.misNum += typeMistake;
            NumState.rightNum += typeLength - typeMistake;
            TypingListener.getInstance().updateNumShow();
            speedButton().setText(String.format("%.2f", ((double) length - mistake) / length * 100) + "%");
            Clipboard.set(result);
        } else {
            Article article = Article.getArticleSingleton();
            int paragraph = article.getParagraph();
            double speed = getSpeed();
            double noMisSpeed = getSpeedNoMistake();
            double keySpeed = getKeySpeed();
            double keyLength = getKeyLength();
            int articleLength = article.getArticleLength();
            double shortCodesNum = (article.getShortCodeEntity().getArticleAverCodes());
            String check = String.format("%.2f", speed + keySpeed + keyLength);
            String checkCode = DoCheck.buildCheckStr(check, "genda");
            String hash = DoCheck.hmacSHA1Encrypt(
                    paragraph + "-" +
                            String.format("%.2f", speed) + "-" +
                            String.format("%.2f", keySpeed) + "-" +
                            String.format("%.2f", keyLength));
            String noMisSpeedStr;
            String enPlayStr = "";
            if (LocalConfig.textMode == Constant.TEXT_MODE_EN) {
                speed = getEnSpeed();
                noMisSpeedStr = enMistake == 0 ? "" : ("/" + String.format("%.2f", getEnSpeedNoMistake()));
                String enNoMisSpeedStr = mistake == 0 ? "" : ("/" + String.format("%.2f", getSpeedNoMistake()));
                enPlayStr = " 词速" + String.format("%.2f", getSpeed()) + enNoMisSpeedStr + "WPM";
            } else {
                noMisSpeedStr = mistake == 0 ? "" : ("/" + String.format("%.2f", noMisSpeed));
            }
            String lookPlayStr = LocalConfig.typingPattern.equals(Constant.WATCH_PLAY_PATTERN)
                    ? " 看打模式 错:" + lookMis + " 多:" + lookMore + " 少:" + lookMiss : "";
            result =
                    "第" + paragraph + "段" + enPlayStr +
                            " 速度" + String.format("%.2f", speed) + (LocalConfig.errorPunishment ? noMisSpeedStr : "") +
                            " 击键" + String.format("%.2f", keySpeed) +
                            " 码长" + String.format("%.2f", keyLength) +
                            (LocalConfig.shortCodesNum ? " 标顶理论" + String.format("%.2f", shortCodesNum) : "") +
                            (LocalConfig.articleLength ? " 字数" + articleLength : "") +
                            (LocalConfig.deleteTextNumber ? " 回改" + deleteTextNumber : "") +
                            (LocalConfig.deleteNumber ? " 退格" + deleteNumber : "") +
                            (LocalConfig.mistake ? " 错字" + mistake : "") +
                            (LocalConfig.keyNumber ? " 键数" + keyNumber : "") +
                            (LocalConfig.repeat ? " 选重" + repeat : "") +
                            (LocalConfig.keyAccuracy ? " 键准" + String.format("%.2f", getKeyAccuracy()) + "%" : "") +
                            (LocalConfig.keyMethod ? " 键法" + String.format("%.2f", getKeyMethod()) + "%" +
                                    "(左" + left + ":右" + right + ":空格" + space + ")" : "") +
                            (LocalConfig.wordRate ? " 打词率" + String.format("%.2f", getWordRate()) + "%" : "") +
                            (LocalConfig.repeatRate ? " 选重率" + String.format("%.2f", getRepeatRate()) + "%" : "") + lookPlayStr +
                            (LocalConfig.useTime ? " 用时" + timer.formatSeconds() : "") +
                            (LocalConfig.typeWritingSign ? " 输入法:" + LocalConfig.typeWriting : "") +
                            (LocalConfig.personalTagSign ? " 个签:" + LocalConfig.personalTag : "") +
                            (LocalConfig.checkCode ? " 校验码" + checkCode : "") +
                            " 哈希" + hash +
                            (LocalConfig.changLiuVersion ? " 长流" + FinalConfig.VERSION : "") +
                            (LocalConfig.systemVersion ? " " + SystemUtil.getSystemName() + "版" : "");
        }
        Clipboard.set(result);
        if (LocalConfig.lurk || !SystemUtil.isWindows()) {
            return;
        }
        if (LocalConfig.getArticleOnNet) {
            HttpUtil.doPostObject(HttpAddr.SEND_ROBOT_ARTICLE_ACH, new SendRobotMessageDto(result, NetArticleTools.getSelectGroupId(), UserState.token));
        } else {
            QqOperation.start(QqOperation.SEND_ACHIEVEMENT, qQNameLabel().getText());
        }
    }
}
