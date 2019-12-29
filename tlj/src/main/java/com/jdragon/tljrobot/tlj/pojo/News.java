package com.jdragon.tljrobot.tlj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@TableName("news")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class News extends Model<News> {
	@TableId(type = IdType.AUTO)
	private int id;
	private String newsType;
	private String title;

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	private Date publishTime;
	private String content;
	private boolean isTop;
	private int clickNum;
	private String author;
	private int editorType;
	private String mdContent;
	@Override
	protected Serializable pkVal(){
		return this.id;
	}
	@Override
	public String toString(){
		return "[id="+id+",newsType="+newsType+",title="+title+",publicTime="+publishTime+",content="+content+",isTop="+isTop+"]";
	}
	public static class Def{
		public static final String ID= "id";
		public static final String NEWS_TYPE = "newsType";
		public static final String NEWS_AUTHOR="author";
		public static final String CLICK_NUM = "clickNum";
	}
}
