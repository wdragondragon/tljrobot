package com.jdragon.tljrobot.tlj.service;

import com.jdragon.tljrobot.tlj.pojo.Article;
import com.jdragon.tljrobot.tlj.pojo.RobotHistory;

import java.sql.Date;
import java.util.List;

/**
 * Create by Jdragon on 2020.01.21
 */
public interface RobotService {
    public boolean uploadUnionMatch(Article article,String author);
    public List<RobotHistory> getUnionMatchRank(Date date);
}
