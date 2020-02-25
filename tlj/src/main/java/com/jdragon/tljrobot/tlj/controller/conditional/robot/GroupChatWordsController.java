package com.jdragon.tljrobot.tlj.controller.conditional.robot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdragon.tljrobot.tlj.mappers.RobotUserMapper;
import com.jdragon.tljrobot.tlj.pojo.RobotUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by Jdragon on 2020.02.05
 */
@RestController
@RequestMapping("/robot/chat")
@Api(tags = "机器人水群接口")
public class GroupChatWordsController {
    @Autowired
    RobotUserMapper robotUserMapper;
    @PostMapping("/addNum/{qq}/{addNum}")
    @ApiOperation("增加聊天字数")
    public void addChatNum(@PathVariable long qq, @PathVariable int addNum){
        QueryWrapper<RobotUser> robotUserQueryWrapper = new QueryWrapper<>();
        RobotUser robotUser = robotUserMapper.selectOne(robotUserQueryWrapper.eq(RobotUser.Def.QQ,qq));
        if(robotUser==null){
            robotUser = new RobotUser(qq,addNum);
            robotUserMapper.insert(robotUser);
        }else{
            robotUser.addChatNum(addNum);
            robotUserMapper.updateById(robotUser);
        }
    }
}
