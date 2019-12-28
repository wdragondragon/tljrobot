package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
@TableName("history")
public class History extends Model<History> {
    @TableId("name")
    private String name;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date date;

    private double sudu;

    private double key;

    private double keylength;

    private int number;

    private int deletetext;

    private int delete;

    private int mistake;

    private int repeat;

    private double Keyaccuracy;

    private double Keymethod;

    private double dacilv;

    private double time;

    private String wenben;

    private int duan;

    private double nandu;

    private int id;

    public static class Def {
        public static final String HISTORY_NAME = "name";
    }
}
