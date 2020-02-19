package com.jdragon.tljrobot.robot.newTyping.entry;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Create by Jdragon on 2020.01.17
 */
@Data
@AllArgsConstructor
public class Article {
    private int id;

    private String title;

    private String content;

    public Article(){}
    public Article(String title, String content){
        this.title = title;
        this.content = content;
    }
}
