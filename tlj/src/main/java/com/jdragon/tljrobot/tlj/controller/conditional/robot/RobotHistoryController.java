package com.jdragon.tljrobot.tlj.controller.conditional.robot;

import com.jdragon.tljrobot.tlj.mappers.RobotHistoryMapper;
import com.jdragon.tljrobot.tlj.mappers.UnionMatchMapper;
import com.jdragon.tljrobot.tlj.pojo.RobotHistory;
import com.jdragon.tljrobot.tlj.pojo.UnionMatch;
import com.jdragon.tljrobot.tljutils.DateUtil;
import com.jdragon.tljrobot.tljutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by Jdragon on 2020.01.21
 */
@RestController
@RequestMapping("/robot")
public class RobotHistoryController {

    @Autowired
    UnionMatchMapper unionMatchMapper;

    @Autowired
    RobotHistoryMapper robotHistoryMapper;
    @PostMapping("/uploadHistory")
    public Result uploadHistory(@RequestBody RobotHistory robotHistory){
        if(robotHistory.isMatch()) {
            UnionMatch unionMatch = unionMatchMapper.selectUnionMatchByDate(DateUtil.now());
            if (unionMatch == null) {
                return Result.error("今天无赛文");
            }
        }
        int typeTime = robotHistoryMapper.selectTypeTime(robotHistory.getQq(),robotHistory.getTypeDate(),robotHistory.isMatch());
        if(typeTime==0)robotHistory.setFirst(true);
        else robotHistory.setFirst(false);
        if (robotHistory.insert()) {
            return Result.success("上传成功");
        }
        else return Result.error("上传失败");
    }
}