package com.jdragon.tljrobot.client.entry;


import lombok.Data;

import java.sql.Date;

@Data
public class History{

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

    private int paragraph;

    private int articleId;

    private Date typeDate;
}
