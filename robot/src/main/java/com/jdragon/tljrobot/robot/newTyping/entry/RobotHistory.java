package com.jdragon.tljrobot.robot.newTyping.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jdragon.tljrobot.tljutils.DateUtil;
import lombok.Data;

import java.sql.Date;

@Data
public class RobotHistory {
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
}
