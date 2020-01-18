package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@TableName("allgroupsaiwen")
public class QQUnionMatch extends Model<QQUnionMatch> {
    @TableId
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date saiwenDate;

    private String title;
    @TableField("saiwen")
    private String article;

    private long author;

    public static class Def {
        public static final String SAIWEN_DATA = "saiwendate";
    }

    @Override
    protected Serializable pkVal(){
        return this.saiwenDate;
    }
}
