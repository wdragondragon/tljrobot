package com.jdragon.tljrobot.tlj.controller.conditional.tlj;

import com.jdragon.tljrobot.tlj.mappers.HistroyMapper;
import com.jdragon.tljrobot.tlj.mappers.TljMatchMapper;
import com.jdragon.tljrobot.tlj.pojo.History;
import com.jdragon.tljrobot.tlj.pojo.History2;
import com.jdragon.tljrobot.tlj.pojo.TljMatch;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tlj.service.TljService;
import com.jdragon.tljrobot.tljutils.DateUtil;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;
import com.jdragon.tljrobot.tljutils.TimingMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * Create by Jdragon on 2020.01.17
 */
@Controller
@RequestMapping("/tljMatch")
@Api(tags = "生稿文相关（需登录）")
public class TljMatchController {

    private static TimingMap<String,Integer> openTljMatchUserList = new TimingMap<>();

    @Autowired
    HistroyMapper histroyMapper;

    @Autowired
    TljService tljService;

    @Autowired
    TljMatchMapper tljMatchMapper;
    @GetMapping("/today/{userId}")
    @ApiOperation("获取今日生稿赛文")
    @ResponseBody
    public Result getTodayMatch(@ApiParam(name = "userId",value = "使用userId获取") @PathVariable String userId,
                                @ApiParam(name = "isMobile",value = "是否为手机")  @RequestParam boolean isMobile) {
        User user = (User) Local.getSession(userId);
        History history = histroyMapper.getTljMatchAch(user.getId(),isMobile,DateUtil.now());
        if (history==null) {
            TljMatch tljMatch = tljService.insertTljMatch();
            if(tljMatch!=null) {
                History emptyHistory = new History();
                emptyHistory.setTypeDate(DateUtil.now());
                emptyHistory.setUserId(user.getId());
                emptyHistory.setArticleId(tljMatch.getArticle().getId());
                emptyHistory.setMobile(isMobile);
                if (histroyMapper.insert(emptyHistory)>0) {
                    openTljMatchUserList.put(userId, emptyHistory.getId());
                    return Result.success("获取成功").setResult(tljMatch);
                }
            }
            return Result.error("获取失败");
        } else{
            return Result.error("你今日已获取过赛文").setResult(history);
        }
    }

    @PostMapping("/uploadTljMatchAch/{userId}")
    @ApiOperation("上传生稿赛成绩")
    @ResponseBody
    public Result uploadTljMatchAch(@ApiParam(name = "userId",value = "使用userId上传")@PathVariable String userId
            ,@ApiParam(name = "history",value = "历史成绩Json") @RequestBody History history){
        if (openTljMatchUserList.containsKey(userId)) {
            User user = (User) Local.getSession(userId);
            history.setId(openTljMatchUserList.get(userId));
            if (tljService.uploadTljMatchAch(user.getId(), history)) {
                openTljMatchUserList.remove(userId);
                return Result.success("上传成功");
            } else {
                return Result.error("上传失败");
            }
        } else {
            return Result.error("赛文已过期");
        }
    }
    @PostMapping("/getTljMatchAchByDate/{date}/{userId}")
    @ApiOperation("根据日期获取生稿赛成绩排名")
    @ResponseBody
    public Result getTljMatchAchByDate(@PathVariable Date date, @PathVariable String userId){
        List<History2> historyList = histroyMapper.selectTljMatchAchByDate(date);
        if(historyList.size()>0) {
            return Result.success("获取成功").setResult(historyList);
        } else {
            return Result.success("今日无成绩");
        }
    }
    @PostMapping("/getMobileTljMatchAchByDate/{date}/{userId}")
    @ApiOperation("根据日期获取生稿赛手机成绩排名")
    @ResponseBody
    public Result getMobileTljMatchAchByDate(@PathVariable Date date, @PathVariable String userId){
        List<History2> historyList = histroyMapper.selectMobileTljMatchAchByDate(date);
        if(historyList.size()>0) {
            return Result.success("获取成功").setResult(historyList);
        } else {
            return Result.success("今日无成绩");
        }
    }

    @PostMapping("/getPCTljMatchAchByDate/{date}/{userId}")
    @ApiOperation("根据日期获取生稿赛电脑成绩排名")
    @ResponseBody
    public Result getPCTljMatchAchByDate(@PathVariable Date date, @PathVariable String userId){
        List<History2> historyList = histroyMapper.selectPCTljMatchAchByDate(date);
        if(historyList.size()>0) {
            return Result.success("获取成功").setResult(historyList);
        } else {
            return Result.success("今日无成绩");
        }
    }
}