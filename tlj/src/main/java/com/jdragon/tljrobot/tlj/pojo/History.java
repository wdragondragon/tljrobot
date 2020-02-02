package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@TableName("tlj_history")
public class History extends Model<History> {
    @TableId(type = IdType.AUTO)
    private int id;

    private int userId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date typeDate;

    private double speed;

    private double keySpeed;

    private double keyLength;

    private int number;

    private int deleteText;

    private int deleteNum;

    private int mistake;

    private int repeatNum;

    private double keyAccuracy;

    private double keyMethod;

    private double wordRate;

    private double time;

    private int articleId;

    private int paragraph;

    public static class Def {
        public static final String TLJ_HISTORY_USER_ID = "userId";
    }
    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
