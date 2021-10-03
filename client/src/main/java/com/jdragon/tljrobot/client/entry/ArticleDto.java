package com.jdragon.tljrobot.client.entry;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleDto {
    private Integer id;

    private String title;

    private String content;
}
