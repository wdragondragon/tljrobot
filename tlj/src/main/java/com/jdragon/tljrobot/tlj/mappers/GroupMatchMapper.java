package com.jdragon.tljrobot.tlj.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.tljrobot.tlj.pojo.GroupMatch;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.sql.Date;

/**
 * Create by Jdragon on 2020.02.04
 */
@Repository
public interface GroupMatchMapper extends BaseMapper<GroupMatch> {
    @Results(id = "TljMatchResultMap",value = {
            @Result(column = "holdDate",property = "holdDate",javaType = java.sql.Date.class,id = true),
            @Result(column = "groupId",property = "groupId",id = true),
            @Result(column = "articleId",property = "articleId"),
            @Result(column = "articleId",property = "article" , one=@One(select = "com.jdragon.tljrobot.tlj.mappers.ArticleMapper.selectById",fetchType = FetchType.EAGER))
    })
    @Select("select * from robot_group_match where groupId=#{groupId} and holdDate=#{holdDate}")
    public GroupMatch selectGroupMatchByGroupIdAndDate(long groupId, Date holdDate);
}
