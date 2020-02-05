package com.jdragon.tljrobot.robot.newTyping.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Date;

@Data
public class TljHistory {
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

    @JsonIgnore
    private int isMobile;

    private String userName;


}
