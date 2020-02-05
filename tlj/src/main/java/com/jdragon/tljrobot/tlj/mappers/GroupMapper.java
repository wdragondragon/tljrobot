package com.jdragon.tljrobot.tlj.mappers;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Create by Jdragon on 2020.02.05
 */
@Repository
public interface GroupMapper {
    @Select("select * from robot_group_map")
    List<HashMap<String,Object>> selectGroupMap();
}
