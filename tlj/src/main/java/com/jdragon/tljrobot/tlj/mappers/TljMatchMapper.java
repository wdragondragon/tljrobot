package com.jdragon.tljrobot.tlj.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.tljrobot.tlj.pojo.TljMatch;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * Create by Jdragon on 2020.01.17
 */
@Component
@Mapper
public interface TljMatchMapper extends BaseMapper<TljMatch> {
    @Results(id = "TljMatchResultMap",value = {
        @Result(column = "id",property = "id",id = true),
        @Result(column = "holdDate",property = "holdDate",javaType = java.sql.Date.class),
        @Result(column = "articleId",property = "articleId"),
        @Result(column = "articleId",property = "article" , one=@One(select = "com.jdragon.tljrobot.tlj.mappers.ArticleMapper.selectById",fetchType = FetchType.EAGER))
    })
    @Select("select * from tlj_match where holdDate=#{holdDate}")
    public TljMatch selectTodayTljMatchByDate(Date holdDate);
}
