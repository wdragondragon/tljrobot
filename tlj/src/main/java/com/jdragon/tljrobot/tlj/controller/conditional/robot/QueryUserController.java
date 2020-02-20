package com.jdragon.tljrobot.tlj.controller.conditional.robot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdragon.tljrobot.tlj.constant.GetTljTypeInfoModel;
import com.jdragon.tljrobot.tlj.dto.TljAvgTypeInfo;
import com.jdragon.tljrobot.tlj.mappers.*;
import com.jdragon.tljrobot.tlj.pojo.RobotUser;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Create by Jdragon on 2020.02.05
 */
@RestController
@RequestMapping("/robot/query")
@Api(tags = "机器人查询接口")
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
    @ApiOperation("通过qq获取群上屏记录和水群字数")
    public Result queryGroupTypeInfo(@PathVariable long qq){
        HashMap<String,Object> result = new HashMap<>();
        result.putAll(robotHistoryMapper.selectAvgGroupTypeInfo(qq));
        RobotUser robotUser = robotUserMapper.selectById(qq);
        int chatNum = 0;
        if(robotUser!=null) {
            chatNum = robotUser.getChatNum();
        }
        result.put("chatNum",chatNum);
        return Result.success("获取成功").setResult(result);
    }
    @PostMapping("/tljTypeInfoListOrderBySpeed/{model}")
    @ApiOperation("获取跟打器用户平均速度排名列表")
    public Result queryTljTypeInfoList(@PathVariable int model){
        List<TljAvgTypeInfo> tljAvgTypeInfoList ;
        if(model== GetTljTypeInfoModel.全部成绩.getModel()) {
           tljAvgTypeInfoList = histroyMapper.selectTljAvgTypeInfoList();
        }else if(model==GetTljTypeInfoModel.赛文成绩.getModel()){
            tljAvgTypeInfoList = histroyMapper.selectTljAllMatchAvgTypeInfoList();
        }else{
            tljAvgTypeInfoList = histroyMapper.selectTljMatchAvgTypeInfoList();
        }
        for(TljAvgTypeInfo tljAvgTypeInfo:tljAvgTypeInfoList){
            tljAvgTypeInfo.setUserNum(userMapper.selectOne(new QueryWrapper<User>().eq(User.Def.USERNAME,tljAvgTypeInfo.getUsername())));
        }
        if(tljAvgTypeInfoList.size()>0) {
            return Result.success("获取成功").setResult(tljAvgTypeInfoList);
        } else {
            return Result.success("成员为空");
        }
    }
    @PostMapping("/tljTypeInfo/{username}")
    @ApiOperation("通过用户名查询账号信息")
    public Result queryTljTypeInfo(@PathVariable String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User user = userMapper.selectOne(queryWrapper.eq(User.Def.USERNAME,username));
        if(user!=null){
            TljAvgTypeInfo tljAvgTypeInfo = histroyMapper.selectTljAvgTypeInfoByUsername(username);
            tljAvgTypeInfo.setUserNum(user);
            return Result.success("获取成功").setResult(tljAvgTypeInfo);
        }else{
            return Result.success("用户不存在");
        }
    }
    @PostMapping("/tljAllTypeInfo")
    @ApiOperation("获取跟打器详情")
    public Result queryTljAllTypeInfo(){
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
    @ApiOperation("获取群列表")
    public Result getGroupMap(){
        return Result.success("获取成功").setResult(groupMapper.selectGroupMap());
    }
}
