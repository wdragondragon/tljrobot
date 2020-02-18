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
            "where speed = (select max(speed) from robot_history where a.qq=qq and typeDate=#{typeDate} and isMatch=1) " +
            "and isMatch=1 and typeDate=#{typeDate} order by speed DESC")
    @MapKey("qq")
    public Map<Long,RobotHistory> selectUnionAchRankByDate(Date typeDate);

    @Select("SELECT * FROM robot_history as a " +
            "where isFirst=1 and isMatch=1 and typeDate=#{typeDate} order by speed DESC")
    @MapKey("qq")
    public Map<Long,RobotHistory> selectUnionFirstAchRankByDate(Date typeDate);

    @Results({
            @Result(property = "key",column = "qq"),
            @Result(property = "value",column = "repeatTime")
    })
    @Select("SELECT qq,COUNT(0) as repeatTime from robot_history WHERE isMatch = 1 and typeDate=#{typeDate} group by qq")
    public List<Map<String,Long>> selectUnionRepeatTime(Date typeDate);

    @Select("select count(0) as typeTime from robot_history where qq=#{qq} and typeDate=#{typeDate} and isMatch=#{isMatch}")
    public int selectTypeTime(long qq,Date typeDate,boolean isMatch);


    @Select("select COUNT(0) as num,AVG(speed) as speed,AVG(keySpeed) as keySpeed,AVG(keyLength) as keyLength " +
            "from robot_history GROUP BY qq HAVING qq=#{qq}")
    public Map<String,Object> selectAvgGroupTypeInfo(long qq);

    
}