package com.jdragon.tljrobot.client.event.FArea;


import com.jdragon.tljrobot.client.config.FinalConfig;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.utils.common.Clipboard;
import com.jdragon.tljrobot.client.utils.common.DoCheck;
import com.jdragon.tljrobot.client.utils.common.QqOperation;
import com.jdragon.tljrobot.tljutils.SystemUtil;
import lombok.SneakyThrows;

import static com.jdragon.tljrobot.client.component.SwingSingleton.QQNameLabel;
import static com.jdragon.tljrobot.client.entry.TypingState.*;

public class SendAchievement {
    @SneakyThrows
    public static void start()  {
        Article article = Article.getArticleSingleton();
        int paragraph = article.getParagraph();
        double speed = getSpeed();
        double keySpeed = getKeySpeed();
        double keyLength = getKeyLength();
        int articleLength = article.getArticle().length();
        double shortCodesNum = (article.getShortCodeEntity().getArticleAverCodes());
        String check = String.format("%.2f",speed+keySpeed+keyLength);
        String checkCode = DoCheck.buildCheckStr(check,"genda");
        String result =
                "第" + paragraph + "段"+
                        " 速度" + String.format("%.2f", speed) +
                        " 击键" + String.format("%.2f", keySpeed) +
                        " 码长" + String.format("%.2f", keyLength) +
                        " 标顶理论" + String.format("%.2f", shortCodesNum) +
                        " 字数" + articleLength +
                        " 回改" + deleteTextNumber +
                        " 退格" + deleteNumber +
                        " 错字" + mistake +
                        " 键数" + keyNumber +
                        " 选重" + repeat +
                        " 键准" + String.format("%.2f", getKeyAccuracy()) + "%" +
                        " 键法" + String.format("%.2f", getKeyMethod()) + "%" +
                        "(左" + left + ":右" + right + ":空格" + space + ")" +
                        " 打词率" + String.format("%.2f", getWordRate())+ "%" +
                        " 选重率" + String.format("%.2f", getRepeatRate()) + "%" +
                        " 拖拉机跟打器 " + FinalConfig.VERSION +
                        " " + SystemUtil.getSystemName() + "版"+
                        " 校验码"+checkCode;
//        if(LocalConfig.typingPattern.equals(Constant.FOLLOW_PLAY_PATTERN)){
//
//        }
        Clipboard.set(result);
        QqOperation.start(QqOperation.SEND_ACHIEVEMENT,QQNameLabel().getText());
    }
}
