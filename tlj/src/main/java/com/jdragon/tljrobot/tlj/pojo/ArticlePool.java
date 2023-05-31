package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author JDragon
 * @date 2023/5/31 11:24
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("article_pool")
@AllArgsConstructor
public class ArticlePool extends Model<ArticlePool> {
    @TableId(type = IdType.AUTO)
    private int id;

    private String title;

    private String content;

    public ArticlePool() {
    }

    public ArticlePool(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
