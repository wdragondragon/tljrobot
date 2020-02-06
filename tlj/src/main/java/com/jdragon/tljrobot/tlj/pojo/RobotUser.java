package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * Create by Jdragon on 2020.02.05
 */
@Data
@TableName("robot_user")
public class RobotUser extends Model<RobotUser> {
    @TableId
    long qq;

    int chatNum;
    public RobotUser(long qq,int chatNum){
        this.qq = qq;
        this.chatNum = chatNum;
    }
    public void addChatNum(int chatNum){
        this.chatNum += chatNum;
    }
    @Override
    protected Serializable pkVal() {
        return this.qq;
    }
    public static class Def {
        public static final String QQ = "qq";
    }
}
