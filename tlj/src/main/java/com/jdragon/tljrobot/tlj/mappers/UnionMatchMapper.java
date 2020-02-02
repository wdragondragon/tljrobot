package com.jdragon.tljrobot.tlj.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.tljrobot.tlj.pojo.UnionMatch;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Mapper
public interface UnionMatchMapper extends BaseMapper<UnionMatch> {
    @Results(id = "TljMatchResultMap",value = {
            @Result(column = "id",property = "id",id = true),
            @Result(column = "holdDate",property = "holdDate",javaType = java.sql.Date.class),
            @Result(column = "articleId",property = "articleId"),
            @Result(column = "articleId",property = "article" , one=@One(select = "com.jdragon.tljrobot.tlj.mappers.ArticleMapper.selectById",fetchType = FetchType.EAGER))
    })
    @Select("select * from robot_match where holdDate=#{holdDate}")
    public UnionMatch selectUnionMatchByDate(Date holdDate);

    @Select("select * from robot_match order by holdDate desc limit 1")
    public UnionMatch selectLastUnionMatch();

}
