package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tlj_user")
public class User extends Model<User> {
    @TableId
    private int id;

    private String username;
    @JsonIgnore
    @TableField
    private String password;

    private int num;

    private int rightNum;

    private int misNum;

    private int dateNum;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date regDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date lastLoginDate;

    private String email;

    private String token;
    public User(int id,String username, String password, int num, int rightNum, int misNum, int dateNum,
                 Date regDate, Date lastLoginDate, String email, String token){
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.num = num;
        this.rightNum = rightNum;
        this.misNum = misNum;
        this.dateNum = dateNum;
        this.regDate = regDate;
        this.lastLoginDate = lastLoginDate;
        this.email = email;
        this.token = token;
    }
    public User(String username, String password){
        this.username = username;
        this.password = password;
        num=0;
        rightNum =0;
        misNum =0;
        dateNum =0;
        email=null;
        java.util.Date nDate = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = sdf.format(nDate);
        Date now = Date.valueOf(sDate);
        regDate = now;
        lastLoginDate = now;
    }
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    public static class Def {
        public static final String USER_NUM = "num";
        public static final String USER_NMAE = "username";
    }
    public String toString(){
        return "[username:"+username+",num:"+num+",rightNum:"+rightNum+",misNum:"+misNum+"]";
    }
}
