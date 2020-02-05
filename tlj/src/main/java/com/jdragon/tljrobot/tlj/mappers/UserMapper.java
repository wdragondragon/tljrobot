package com.jdragon.tljrobot.tlj.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.tljrobot.tlj.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("update tlj_user set dateNum=0")
    int dateNumInit();

    @Select("SELECT count(0) as userNum,SUM(num) as userTypeNum,SUM(rightNum) as userRightNum,SUM(misNum) as userMisNum,SUM(dateNum) as userDateNum from tlj_user")
    HashMap<String,Object> selectNumSum();

    @Select("SELECT username,dateNum as userMaxNum from tlj_user WHERE dateNum=(select MAX(dateNum) from tlj_user)")
    HashMap<String,Object> selectMaxNumUser();
}
