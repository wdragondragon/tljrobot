package com.jdragon.tljrobot.tlj.dto;

import com.jdragon.tljrobot.tlj.pojo.User;
import lombok.Data;

/**
 * Create by Jdragon on 2020.02.14
 */
@Data
public class TljAvgTypeInfo {
    private String username;
    private double speed;
    private double keyLength;
    private double keySpeed;
    private double count;
    private int num;
    private int rightNum;
    private int misNum;
    private int dateNum;
    public void setUserNum(User user){
        num = user.getNum();
        rightNum = user.getRightNum();
        misNum = user.getMisNum();
        dateNum = user.getDateNum();
    }
}
