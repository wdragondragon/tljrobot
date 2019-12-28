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
@TableName("client")
public class User extends Model<User> {
    @TableId("username")
    private String name;
    @JsonIgnore
    @TableField("password")
    private String pwd;

    private int num;

    private int rightnum;

    private int misnum;

    private int datenum;

    private int online;

    private double aver;

    private int n;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date zhucedate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date lastdate;

    @TableField("email")
    private String email;
    public User(String name,String pwd,int num,int rightnum,int misnum,int datenum,int online,
                double aver,int n,Date zhucedate,Date lastdate,String email){
        super();
        this.name = name;
        this.pwd = pwd;
        this.num = num;
        this.rightnum = rightnum;
        this.misnum = misnum;
        this.datenum = datenum;
        this.online = online;
        this.aver = aver;
        this.n = n;
        this.zhucedate = zhucedate;
        this.lastdate = lastdate;
        this.email = email;
    }
    public User(String name,String pwd){
        this.name = name;
        this.pwd = pwd;
        num=0;rightnum=0;misnum=0;online=0;datenum=0;
        aver=0;n=0;email=null;
        java.util.Date nDate = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = sdf.format(nDate);
        Date now = Date.valueOf(sDate);
        zhucedate = now;
        lastdate = now;
    }
    @Override
    public String toString(){
        return "[name="+name+"]";
    }
    @Override
    protected Serializable pkVal() {
        return this.name;
    }
    public static class Def {
        public static final String USER_NUM = "num";
        public static final String USER_NMAE = "username";
    }
}
