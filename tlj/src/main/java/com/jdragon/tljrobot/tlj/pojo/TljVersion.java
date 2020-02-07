package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * Create by Jdragon on 2020.02.07
 */
@Data
@TableName("tlj_version")
public class TljVersion extends Model<TljVersion> {
    @TableId
    private String version;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date updateDate;

    @Override
    protected Serializable pkVal(){
        return this.version;
    }

    public static class Def{
        public final static String UPDATE_DATE = "updateDate";
    }
}
