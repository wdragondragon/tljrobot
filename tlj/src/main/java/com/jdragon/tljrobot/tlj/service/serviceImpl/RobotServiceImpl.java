package com.jdragon.tljrobot.tlj.service.serviceImpl;

import com.jdragon.tljrobot.tlj.mappers.ArticleMapper;
import com.jdragon.tljrobot.tlj.mappers.RobotHistoryMapper;
import com.jdragon.tljrobot.tlj.mappers.UnionMatchMapper;
import com.jdragon.tljrobot.tlj.pojo.Article;
import com.jdragon.tljrobot.tlj.pojo.RobotHistory;
import com.jdragon.tljrobot.tlj.pojo.UnionMatch;
import com.jdragon.tljrobot.tlj.service.RobotService;
import com.jdragon.tljrobot.tljutils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

/**
 * Create by Jdragon on 2020.01.21
 */
@Service
public class RobotServiceImpl implements RobotService {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UnionMatchMapper unionMatchMapper;

    @Autowired
    RobotHistoryMapper robotHistoryMapper;
    @Override
    public boolean uploadUnionMatch(Article articleTemp, String author) {
        Article article = articleMapper.selectArticleByContent(articleTemp.getTitle(),articleTemp.getContent());
        if(article==null){
            if(articleTemp.insert()){
                article = articleTemp;
            }else return false;
        }
        UnionMatch unionMatch = unionMatchMapper.selectLastUnionMatch();
        Date date = DateUtil.now();
        //判断赛文最大日期，添加赛文时往最大日期后加。
        //若最大日期小于今天，则加在今天，以防添加赛文在今天之前。
        if(unionMatch!=null&&date.getTime()<unionMatch.getHoldDate().getTime()){
            date = unionMatch.getHoldDate();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE,1);
            java.util.Date date1 =  calendar.getTime();
            date = new Date(date1.getTime());
        }
        unionMatch = new UnionMatch(article,date,author);
        if(unionMatch.insert())
            return true;
        else return false;
    }

    @Override
    public List<RobotHistory> getUnionMatchRank(Date date) {
        Map<Long, RobotHistory> robotHistoryMap = robotHistoryMapper.selectUnionAchRankByDate(date);
        List<Map<String, Long>> typeNumMapList = robotHistoryMapper.selectUnionRepeatTime(date);

        List<RobotHistory> robotHistoryList = new ArrayList<>(robotHistoryMap.values());
        Collections.sort(robotHistoryList, (o1,o2)->Double.valueOf(o2.getSpeed()).compareTo(o1.getSpeed()));

        for(RobotHistory robotHistory:robotHistoryList){
            for(Map<String, Long> repeatMap : typeNumMapList) {
                if(repeatMap.get("key")==robotHistory.getQq()) {
                    robotHistory.setTypeNum(Integer.parseInt(String.valueOf(repeatMap.get("value"))));
                    break;
                }
            }
        }
        return robotHistoryList;
    }

    @Override
    public List<RobotHistory> getUnionFirstMatchRank(Date date) {
        Map<Long, RobotHistory> robotHistoryMap = robotHistoryMapper.selectUnionFirstAchRankByDate(date);
        List<Map<String, Long>> typeNumMapList = robotHistoryMapper.selectUnionRepeatTime(date);

        List<RobotHistory> robotHistoryList = new ArrayList<>(robotHistoryMap.values());
        Collections.sort(robotHistoryList, (o1,o2)->Double.valueOf(o2.getSpeed()).compareTo(o1.getSpeed()));

        for(RobotHistory robotHistory:robotHistoryList){
            for(Map<String, Long> repeatMap : typeNumMapList) {
                if(repeatMap.get("key")==robotHistory.getQq()) {
                    robotHistory.setTypeNum(Integer.parseInt(String.valueOf(repeatMap.get("value"))));
                    break;
                }
            }
        }
        return robotHistoryList;
    }
}
