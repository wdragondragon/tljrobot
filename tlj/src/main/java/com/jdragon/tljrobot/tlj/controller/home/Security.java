package com.jdragon.tljrobot.tlj.controller.home;

import com.jdragon.tljrobot.tlj.mappers.UserMapper;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
@Api(tags = "无条件操作")
public class Security {
    @Autowired
    private UserMapper userMapper;
    @GetMapping(value = "/login")
    @ApiOperation("进入登录界面")
    public String Login(){
        return "login";
    }
    @GetMapping(value = "/register")
    @ApiOperation(value = "进入注册界面")
    public String register(){
        return "register";
    }
    @PostMapping(value = "/login/{username}/{password}")
    @ApiOperation(value = "登录接口")
    @ResponseBody
    public Result Login(@ApiParam(name = "username",value = "用户名")@PathVariable("username") String name,
                        @ApiParam(name = "password",value = "登录密码")@PathVariable("password") String pwd){
        User user1 = userMapper.selectById(name);
        if(user1==null){
            return  Result.success("无该用户");
        }else if(!user1.getPwd().equals(pwd)){
            return  Result.success("密码错误");
        }else{
            String userId = Local.login(user1);
            return Result.success("登录成功").setResult(userId);
        }
    }

    @PostMapping(value = "/logout/{userId}")
    @ApiOperation("退出登录")
    @ResponseBody
    public Result Logout(@ApiParam(name = "userId",value = "使用userId退出")@PathVariable String userId){
        Local.logout(userId);
        return Result.success("退出成功");
    }

    @PostMapping(value = "/register/{name}/{pwd}")
    @ApiOperation(value = "注册接口")
    @ResponseBody
    public Result register(@ApiParam(name = "name",value = "用户名")@PathVariable String name,
                           @ApiParam(name = "pwd",value = "密码")@PathVariable String pwd){
        User user = userMapper.selectById(name);
        if(user==null){
            user = new User(name, pwd);
            if(user.insert())
                return Result.success("注册成功");
            else
                return Result.error("注册失败");
        }else {
            return Result.error("已存在");
        }
    }
}
