package com.jdragon.tljrobot.tlj.controller.conditional.robot;

import com.jdragon.tljrobot.tlj.mappers.HistroyMapper;
import com.jdragon.tljrobot.tlj.mappers.TljMatchMapper;
import com.jdragon.tljrobot.tlj.pojo.History2;
import com.jdragon.tljrobot.tlj.pojo.TljMatch;
import com.jdragon.tljrobot.tljutils.DateUtil;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * Create by Jdragon on 2020.02.04
 */
@Controller
@RequestMapping("/robot/tljMatch")
@Api(tags = "跟打器生稿赛")
public class TljMatchInRobotController {
    @Autowired
    HistroyMapper histroyMapper;

    @Autowired
    TljMatchMapper tljMatchMapper;

    @GetMapping("/getTljMatch/{date}")
    @ApiOperation("根据日期获取跟打器赛文")
    @ResponseBody
    public Result getTljMatchDate(@PathVariable Date date){
        if(date.after(DateUtil.now())){
            return Result.error("还没发布呢，不能获取今天之后的赛文哦");
        }
        TljMatch tljMatch = tljMatchMapper.selectTljMatchByDate(DateUtil.now());
        if(tljMatch==null){
            return Result.success(date+"无赛文");
        }else{
            return Result.success("获取成功").setResult(tljMatch);
        }
    }
    @PostMapping("/getTljMatchAchByDate/{date}")
    @ApiOperation("根据日期获取跟打器赛文成绩")
    @ResponseBody
    public Result getTljMatchAchByDate(@PathVariable Date date){
        List<History2> historyList = histroyMapper.selectTljMatchAchByDate(date);
        if(historyList.size()>0) {
            return Result.success("获取成功").setResult(historyList);
        } else {
            return Result.success("今日无成绩");
        }
    }
    @ApiOperation("根据日期获取跟打器手机端赛文成绩")
    @PostMapping("/getMobileTljMatchAchByDate/{date}")
    @ResponseBody
    public Result getMobileTljMatchAchByDate(@PathVariable Date date){
        List<History2> historyList = histroyMapper.selectMobileTljMatchAchByDate(date);
        if(historyList.size()>0) {
            return Result.success("获取成功").setResult(historyList);
        } else {
            return Result.success("今日无成绩");
        }
    }
    @ApiOperation("根据然获取跟打器PC端赛文成绩")
    @PostMapping("/getPCTljMatchAchByDate/{date}")
    @ResponseBody
    public Result getPCTljMatchAchByDate(@PathVariable Date date){
        List<History2> historyList = histroyMapper.selectPCTljMatchAchByDate(date);
        if(historyList.size()>0) {
            return Result.success("获取成功").setResult(historyList);
        } else {
            return Result.success("今日无成绩");
        }
    }
}
