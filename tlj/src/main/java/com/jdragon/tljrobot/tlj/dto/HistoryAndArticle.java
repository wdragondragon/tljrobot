package com.jdragon.tljrobot.tlj.dto;

import com.jdragon.tljrobot.tlj.pojo.Article;
import com.jdragon.tljrobot.tlj.pojo.History;
import lombok.Data;

@Data
public class HistoryAndArticle {
    History history;
    Article article;
}
