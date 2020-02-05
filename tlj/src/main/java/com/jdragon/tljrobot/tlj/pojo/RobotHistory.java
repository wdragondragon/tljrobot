package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jdragon.tljrobot.tljutils.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@TableName("robot_history")
public class RobotHistory extends Model<RobotHistory> {
    @TableId(type = IdType.AUTO)
    private int id;

    private long qq;

    private long groupId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date typeDate;

    private double speed;

    private double keySpeed;

    private double keyLength;

    private int deleteText;

    private int deleteNum;

    private int mistake;
    //选重
    private int repeatNum;

    private double keyAccuracy;

    private boolean isMatch;

    private boolean isFirst;

    @TableField(exist = false)
    private int typeNum;
    public static class Def {
        public static final String SAIWEN_DATE = "saiwendate";
    }
    public RobotHistory(){}
    public RobotHistory(long userQQ, double speed, double keySpeed, double keyLength){
        this.speed = speed;
        this.keyLength = keyLength;
        this.keySpeed = keySpeed;
        this.qq = userQQ;
        typeDate = DateUtil.now();
    }
    @Override
    protected Serializable pkVal(){
        return this.id;
    }
}
