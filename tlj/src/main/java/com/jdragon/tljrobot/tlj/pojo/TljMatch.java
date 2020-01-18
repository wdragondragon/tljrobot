package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

/**
 * Create by Jdragon on 2020.01.17
 */
@Data
@TableName("tlj_match")
public class TljMatch extends Model<TljMatch> {
    @TableId
    private int id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date holdDate;

    @TableField("articleId")
    private Article article;
}
