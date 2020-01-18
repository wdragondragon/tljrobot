package com.jdragon.tljrobot.tlj.controller.conditional;

import com.jdragon.tljrobot.tlj.mappers.HistroyMapper;
import com.jdragon.tljrobot.tlj.mappers.TljMatchMapper;
import com.jdragon.tljrobot.tlj.pojo.History;
import com.jdragon.tljrobot.tlj.pojo.TljMatch;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tljutils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Create by Jdragon on 2020.01.17
 */
@Controller
@RequestMapping("/tljMatch")
public class TljMatchController {
    @Autowired
    TljMatchMapper tljMatchMapper;
    @Autowired
    HistroyMapper histroyMapper;
    @GetMapping("/today/{userId}")
    @ResponseBody
    public Result getTodayMatch(@PathVariable String userId) {
        User user = (User) Local.getSession(userId);
        History history = histroyMapper.getTljMatchAch(user.getId(), DateUtil.now());
        if (history!=null) {
            return Result.error("你今日已获取过赛文").setResult(history);
        } else{
            TljMatch tljMatch = tljMatchMapper.selectTodayTljMatchByDate(DateUtil.now());
            History emptyHistory = new History();
            emptyHistory.setTypeDate(DateUtil.now());
            emptyHistory.setUserId(user.getId());
            emptyHistory.setArticleId(tljMatch.getArticle().getId());
            if(emptyHistory.insert()) {
                openTljMatchUserList.put(userId,emptyHistory.getId());
                return Result.success("获取成功").setResult(tljMatch);
            }
            return Result.error("获取失败");
        }
    }
    public static TimingMap<String,Integer> openTljMatchUserList = new TimingMap<>();

    @PostMapping("/uploadAch/{userId}")
    @ResponseBody
    public Result uploadTljMatchAch(@PathVariable String userId,@RequestBody History history){
        if(!openTljMatchUserList.containsKey(userId))return Result.error("赛文已过期");
        User user = (User)Local.getSession(userId);
        TljMatch tljMatch = tljMatchMapper.selectTodayTljMatchByDate(DateUtil.now());
        history.setArticleId(tljMatch.getArticle().getId());
        history.setUserId(user.getId());
        history.setTypeDate(DateUtil.now());
        history.setId(openTljMatchUserList.get(userId));
        history.updateById();
        System.out.println(history.getSpeed());
        return Result.success("上传成功");
    }
}