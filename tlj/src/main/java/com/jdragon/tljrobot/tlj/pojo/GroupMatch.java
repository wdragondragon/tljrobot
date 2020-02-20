package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * Create by Jdragon on 2020.02.04
 */
@Data
@TableName("robot_group_match")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupMatch extends Model<GroupMatch> {
    @TableId
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date holdDate;

    @TableId
    private long groupId;

    private int articleId;

    @TableField(exist = false)
    private Article article;



    public static class Def{
        public static final String GROUP_ID = "groupId";
        public static final String HOLD_DATE = "holdDate";
    }
    @Override
    protected Serializable pkVal() {
        return this.holdDate;
    }
}
