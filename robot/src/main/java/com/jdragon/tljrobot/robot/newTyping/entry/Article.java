package com.jdragon.tljrobot.robot.newTyping.entry;

import lombok.Data;

/**
 * Create by Jdragon on 2020.01.17
 */
@Data
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
