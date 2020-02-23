package com.jdragon.tljrobot.client.entry;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoryDto {
    ArticleDto article;
    History history;
}
