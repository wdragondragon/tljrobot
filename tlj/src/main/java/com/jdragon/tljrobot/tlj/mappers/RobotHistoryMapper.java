package com.jdragon.tljrobot.tlj.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.tljrobot.tlj.pojo.RobotHistory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface RobotHistoryMapper extends BaseMapper<RobotHistory> {

    @Select("SELECT * FROM robot_history as a " +
            "where speed = (select max(speed) from robot_history where a.qq=qq) " +
            "and isMatch=1 and typeDate=#{typeDate} order by speed DESC")
    @MapKey("qq")
    public Map<Long,RobotHistory> selectUnionAchRankByDate(Date typeDate);

    @Results({
            @Result(property = "key",column = "qq"),
            @Result(property = "value",column = "repeatTime")
    })
    @Select("SELECT qq,COUNT(0) as repeatTime from robot_history WHERE isMatch = 1 and typeDate=#{typeDate} group by qq")
    public List<Map<String,Long>> selectUnionRepeatTime(Date typeDate);
}