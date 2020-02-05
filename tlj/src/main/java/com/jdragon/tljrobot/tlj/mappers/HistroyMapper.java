package com.jdragon.tljrobot.tlj.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.tljrobot.tlj.pojo.History;
import com.jdragon.tljrobot.tlj.pojo.History2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

@Component
@Mapper
public interface HistroyMapper extends BaseMapper<History> {
    @Select("select * from tlj_history where userId=#{userId} order by id DESC")
    List<History> getHistoryByUserId(int userId);

    @Select("select * from tlj_history where userId=#{userId} and isMobile=#{isMobile} and paragraph=0 and typeDate=#{typeDate}")
    History getTljMatchAch(int userId,boolean isMobile,java.sql.Date typeDate);

    @Select("select u.username,h.* from tlj_history as h join tlj_user as u on u.id=h.userId" +
            " where paragraph=0 and typeDate=#{typeDate} order by speed DESC")
    List<History2> selectTljMatchAchByDate(Date typeDate);

    @Select("select u.username,h.* from tlj_history as h join tlj_user as u on u.id=h.userId" +
            " where isMobile=1 and paragraph=0 and typeDate=#{typeDate} order by speed DESC")
    List<History2> selectMobileTljMatchAchByDate(Date typeDate);

    @Select("select u.username,h.* from tlj_history as h join tlj_user as u on u.id=h.userId" +
            " where isMobile=0 and paragraph=0 and typeDate=#{typeDate} order by speed DESC")
    List<History2> selectPCTljMatchAchByDate(Date typeDate);

    @Select("select count(0) as num,AVG(speed) as speed ,AVG(keyLength) as keyLength,AVG(keySpeed) as keySpeed " +
            "from tlj_history as h join tlj_user as u on u.id=h.userId where username=#{username}")
    HashMap<String,Object> selectTljAvgTypeInfoByUsername(String username);

    @Select("SELECT count(0) as num,SUM(number) as number,SUM(time) as time from tlj_history")
    HashMap<String,Object> selectTljAvgTypeInfo();
}