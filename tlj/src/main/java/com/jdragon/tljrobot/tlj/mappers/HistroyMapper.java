package com.jdragon.tljrobot.tlj.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.tljrobot.tlj.pojo.History;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface HistroyMapper extends BaseMapper<History> {
    @Select("select * from tlj_history where userId=#{userId} order by id DESC")
    List<History> getHistoryByUserId(int userId);

    @Select("select * from tlj_history where userId=#{userId} and paragraph=0 and typeDate=#{typeDate}")
    History getTljMatchAch(int userId,java.sql.Date typeDate);

}