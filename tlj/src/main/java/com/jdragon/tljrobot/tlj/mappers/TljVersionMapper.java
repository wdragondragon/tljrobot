package com.jdragon.tljrobot.tlj.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdragon.tljrobot.tlj.pojo.TljVersion;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Create by Jdragon on 2020.02.07
 */
@Repository
public interface TljVersionMapper extends BaseMapper<TljVersion> {
    @Select("select * from tlj_version ORDER BY updateDate DESC limit 1 ")
    TljVersion selectNewVersion();
}
