package com.jdragon.tljrobot.tlj.controller.conditional.robot;

import com.jdragon.tljrobot.tlj.mappers.HistroyMapper;
import com.jdragon.tljrobot.tlj.pojo.History2;
import com.jdragon.tljrobot.tljutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.List;

/**
 * Create by Jdragon on 2020.02.04
 */
@Controller
@RequestMapping("/robot/tljMatch")
public class TljMatchInRobotController {
    @Autowired
    HistroyMapper histroyMapper;

    @PostMapping("/getTljMatchAchByDate/{date}")
    @ResponseBody
    public Result getTljMatchAchByDate(@PathVariable Date date){
        List<History2> historyList = histroyMapper.selectTljMatchAchByDate(date);
        if(historyList.size()>0)
            return Result.success("获取成功").setResult(historyList);
        else
            return Result.success("今日无成绩");
    }
    @PostMapping("/getMobileTljMatchAchByDate/{date}")
    @ResponseBody
    public Result getMobileTljMatchAchByDate(@PathVariable Date date){
        List<History2> historyList = histroyMapper.selectMobileTljMatchAchByDate(date);
        if(historyList.size()>0)
            return Result.success("获取成功").setResult(historyList);
        else
            return Result.success("今日无成绩");
    }

    @PostMapping("/getPCTljMatchAchByDate/{date}")
    @ResponseBody
    public Result getPCTljMatchAchByDate(@PathVariable Date date){
        List<History2> historyList = histroyMapper.selectPCTljMatchAchByDate(date);
        if(historyList.size()>0)
            return Result.success("获取成功").setResult(historyList);
        else
            return Result.success("今日无成绩");
    }
}
