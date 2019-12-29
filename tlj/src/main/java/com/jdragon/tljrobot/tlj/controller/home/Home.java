package com.jdragon.tljrobot.tlj.controller.home;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdragon.tljrobot.tlj.mappers.NewsMapper;
import com.jdragon.tljrobot.tlj.mappers.UserMapper;
import com.jdragon.tljrobot.tlj.pojo.News;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        return Result.success("获取成功").setResult(pageInfo);
    }
    @RequestMapping("/introduce")
    public String getIntroduce(){
        return "introduce";
    }
    @RequestMapping("/myInfo")
    public String getMyInfo(){
        return "myInfo";
    }
    @RequestMapping("/allUser")
    public String getAllUser(){
        return "allUser";
    }

}
