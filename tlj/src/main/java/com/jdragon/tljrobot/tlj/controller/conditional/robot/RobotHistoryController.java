package com.jdragon.tljrobot.tlj.controller.conditional.robot;

import com.jdragon.tljrobot.tlj.mappers.RobotHistoryMapper;
import com.jdragon.tljrobot.tlj.mappers.UnionMatchMapper;
import com.jdragon.tljrobot.tlj.pojo.RobotHistory;
import com.jdragon.tljrobot.tlj.pojo.UnionMatch;
import com.jdragon.tljrobot.tljutils.DateUtil;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "群历史跟打")
public class RobotHistoryController {

    @Autowired
    UnionMatchMapper unionMatchMapper;

    @Autowired
    RobotHistoryMapper robotHistoryMapper;
    @PostMapping("/uploadHistory")
    @ApiOperation("收集群日赛上屏成绩")
    public Result uploadHistory(@RequestBody RobotHistory robotHistory){
        if(robotHistory.isMatch()) {
            UnionMatch unionMatch = unionMatchMapper.selectUnionMatchByDate(DateUtil.now());
            if (unionMatch == null) {
                return Result.error("今天无赛文");
            }
        }
        int typeTime = robotHistoryMapper.selectTypeTime(robotHistory.getQq(),robotHistory.getTypeDate(),robotHistory.isMatch());
        robotHistory.setFirst(typeTime == 0);
        if (robotHistoryMapper.insert(robotHistory)>0) {
            return Result.success("上传成功");
        }
        else {
            return Result.error("上传失败");
        }
    }
}
