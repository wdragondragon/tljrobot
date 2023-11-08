package com.jdragon.tljrobot.client.utils.common;

import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.History;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.tljutils.DateUtil;

public class HistoryUtil {
    public static History getHistoryEntry() {
        History history = new History();
        history.setSpeed(TypingState.getSpeed());
        history.setKeySpeed(TypingState.getKeySpeed());
        history.setKeyLength(TypingState.getKeyLength());
        history.setNumber(Article.getArticleSingleton().getArticleLength());
        history.setDeleteNum(TypingState.deleteNumber);
        history.setDeleteText(TypingState.deleteTextNumber);
        history.setKeyAccuracy(TypingState.getKeyAccuracy());
        history.setKeyMethod(TypingState.getKeyMethod());
        history.setMistake(TypingState.mistake);
        history.setRepeatNum(TypingState.repeat);
        history.setTime(TypingState.timer.getSecond());
        history.setWordRate(TypingState.getWordRate());
        history.setParagraph(Article.getArticleSingleton().getParagraph());
        history.setTypeDate(DateUtil.now());
        return history;
    }
}
