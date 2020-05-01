package com.jdragon.tljrobot.tlj.controller.home;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdragon.tljrobot.tlj.mappers.UserMapper;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
@Api(tags = "无条件操作")
public class Home {
    @Autowired
    private UserMapper userMapper;
    @PostMapping(value = "/allUser/{page}")
    @ApiOperation(value = "获取全部用户信息", response = User.class)
    @ApiResponse(code = 200, message = "获取成功")
    @ResponseBody
    public Result AllUser(@PathVariable int page){
        PageHelper.startPage(page,20,true);
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.orderByDesc(User.Def.USER_NUM);
        List<User> users = userMapper.selectList(queryWrapper);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return Result.success("获取成功").setResult(pageInfo);
    }
    @GetMapping("/introduce")
    @ApiOperation("进入主页面")
    public String getIntroduce(){
        return "introduce";
    }
    @GetMapping("/myInfo")
    @ApiOperation("进入我的信息页面")
    public String getMyInfo(){
        return "myInfo";
    }
    @GetMapping("/allUser")
    @ApiOperation("进入全体概况页面")
    public String getAllUser(){
        return "allUser";
    }

}
