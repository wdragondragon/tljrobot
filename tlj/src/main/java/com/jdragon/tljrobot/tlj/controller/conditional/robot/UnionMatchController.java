package com.jdragon.tljrobot.tlj.controller.conditional.robot;

import com.jdragon.tljrobot.tlj.mappers.UnionMatchMapper;
import com.jdragon.tljrobot.tlj.pojo.Article;
import com.jdragon.tljrobot.tlj.pojo.RobotHistory;
import com.jdragon.tljrobot.tlj.pojo.UnionMatch;
import com.jdragon.tljrobot.tlj.service.RobotService;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * Create by Jdragon on 2020.01.21
 */
@RestController
@RequestMapping("/robot/unionMatch")
@Api(tags = "机器人联赛")
public class UnionMatchController {

    @Autowired
    UnionMatchMapper unionMatchMapper;

    @Autowired
    RobotService robotService;

    @PostMapping("/uploadUnionMatch")
    @ApiOperation("上传联赛赛文")
    public Result uploadUnionMatch(@RequestParam String author, @RequestBody Article article){
        Date matchDate = robotService.uploadUnionMatch(article,author);
        if(matchDate!=null){
            return Result.success("上传成功:进行日期"+matchDate);
        }
        return Result.error("上传失败");
    }

    @PostMapping("/getUnionMatch/{date}")
    @ApiOperation("根据日期获取联赛赛文")
    public Result getUnionMatch(@PathVariable Date date){
        UnionMatch unionMatch = unionMatchMapper.selectUnionMatchByDate(date);
        if(unionMatch!=null) {
            return Result.success("获取成功").setResult(unionMatch);
        } else {
            return Result.error("无赛文");
        }
    }

    @PostMapping("/getUnionAchRank/{date}")
    @ApiOperation("根据日期获取联赛排行")
    public Result getUnionAchRank(@PathVariable Date date){
        List<RobotHistory> robotHistoryList = robotService.getUnionMatchRank(date);
        if(robotHistoryList.size()!=0) {
            return Result.success("获取成功").setResult(robotHistoryList);
        } else {
            return Result.success("今天尚未有人提交成绩");
        }
    }
    @PostMapping("/getUnionFirstAchRank/{date}")
    @ApiOperation("根据日期获取联赛首打排行")
    public Result getUnionFirstAchRank(@PathVariable Date date){
        List<RobotHistory> robotHistoryList = robotService.getUnionFirstMatchRank(date);
        if(robotHistoryList.size()!=0) {
            return Result.success("获取成功").setResult(robotHistoryList);
        } else {
            return Result.success("今天尚未有人提交成绩");
        }
    }
    /**
     * 赛文成绩上传在合并到robotHistoryController
     */
//    @PostMapping("/uploadUnionMatchAch")
//    public Result uploadUnionMatchAch(@RequestBody RobotHistory robotHistory){
//        UnionMatch unionMatch = unionMatchMapper.selectUnionMatchByDate(DateUtil.now());
//        if(unionMatch==null)
//            return Result.error("今天无赛文");
//        if(robotHistory.insert())
//            return Result.success("上传成功");
//        else return Result.error("上传失败");
//    }
}