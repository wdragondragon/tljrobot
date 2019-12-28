package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.sql.Date;
import com.jdragon.tljrobot.tljutils.DateUtil;
import lombok.Data;

@Data
@TableName("allgroupsaiwenchengji")
public class ArticleAchi extends Model<ArticleAchi> {

    private long id;

    private long groupid;

    private Date saiwendate;

    private double sudu;

    private double keyspeed;

    private double keylength;

    private int typenum;

    private String name;

    private int del;

    private int deletetext;
    //选重
    private int sel;

    private int mistake;

    private double rightkeyper;

    private double firstplay;

    public static class Def {
        public static final String SAIWEN_DATE = "saiwendate";
    }
    public ArticleAchi(){}
    public ArticleAchi(String username,long userQQ,double sudu,double keyspeed,double keylength){
        this.sudu = sudu;
        this.keylength = keylength;
        this.keyspeed = keyspeed;
        this.id = userQQ;
        this.name = username;
        groupid = 9999;
        saiwendate = DateUtil.now();
    }
}
