package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * Create by Jdragon on 2020.01.17
 */
@Data
@TableName("tlj_match")
public class TljMatch extends Model<TljMatch> {
    @TableId(type = IdType.AUTO)
    private int id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date holdDate;

    private int articleId;

    private String author;

    @TableField(exist = false)
    private Article article;

    public TljMatch(){}
    public TljMatch(Article article,Date holdDate,String author){
        this.article = article;
        this.articleId = article.getId();
        this.holdDate = holdDate;
        this.author = author;
    }
    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
