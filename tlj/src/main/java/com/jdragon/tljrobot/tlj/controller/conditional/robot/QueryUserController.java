package com.jdragon.tljrobot.tlj.controller.conditional.robot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdragon.tljrobot.tlj.mappers.*;
import com.jdragon.tljrobot.tlj.pojo.RobotUser;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Create by Jdragon on 2020.02.05
 */
@RestController
@RequestMapping("/robot/query")
public class QueryUserController {
    @Autowired
    RobotHistoryMapper robotHistoryMapper;

    @Autowired
    HistroyMapper histroyMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RobotUserMapper robotUserMapper;
    @PostMapping("/groupTypeInfo/{qq}")
    public Result queryGroupTypeInfo(@PathVariable long qq){
        HashMap<String,Object> result = new HashMap<>();
        result.putAll(robotHistoryMapper.selectAvgGroupTypeInfo(qq));
        RobotUser robotUser = robotUserMapper.selectById(qq);
        int chatNum = 0;
        if(robotUser!=null)chatNum = robotUser.getChatNum();
        result.put("chatNum",chatNum);
        return Result.success("获取成功").setResult(result);
    }

    @PostMapping("/tljTypeInfo/{username}")
    public Result queryTljTypeInfo(@PathVariable String username){
        System.out.println(username);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User user = userMapper.selectOne(queryWrapper.eq(User.Def.USERNAME,username));
        if(user!=null){
            HashMap<String,Object> hashMap = histroyMapper.selectTljAvgTypeInfoByUsername(username);
            hashMap.put("allNum",user.getNum());
            hashMap.put("rightNum",user.getRightNum());
            hashMap.put("misNum",user.getMisNum());
            hashMap.put("dateNum",user.getDateNum());
            return Result.success("获取成功").setResult(hashMap);
        }else{
            return Result.success("用户不存在");
        }
    }
    @PostMapping("/tljAllTypeInfo")
    public Result tljTypeInfo(){
        HashMap<String,Object> result = new HashMap<>();
        result.putAll(histroyMapper.selectTljAvgTypeInfo());
        result.putAll(userMapper.selectNumSum());
        result.putAll(userMapper.selectMaxNumUser());
        result.put("onlineNum", Local.size());
        return Result.success("获取成功").setResult(result);
    }
    @Autowired
    GroupMapper groupMapper;
    @PostMapping("/getGroupMap")
    public Result getGroupMap(){
        return Result.success("获取成功").setResult(groupMapper.selectGroupMap());
    }
}
