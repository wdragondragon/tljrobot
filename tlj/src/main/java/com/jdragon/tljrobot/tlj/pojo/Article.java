package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Create by Jdragon on 2020.01.17
 */
@Data
@TableName("all_article")
@AllArgsConstructor
public class Article extends Model<Article> {
    @TableId(type = IdType.AUTO)
    private int id;

    private String title;

    private String content;

    public Article(){}
    public Article(String title,String content){
        this.title = title;
        this.content = content;
    }
    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
