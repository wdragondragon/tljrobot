package com.jdragon.tljrobot.tlj.controller.home;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    public Result Login(@ApiParam(name = "username",value = "用户名")@PathVariable("username") String username,
                        @ApiParam(name = "password",value = "登录密码")@PathVariable("password") String password){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq(User.Def.USER_NMAE,username));
        if(user==null){
            return Result.success("无该用户");
        }else if(!user.getPassword().equals(password)){
            return Result.success("密码错误");
        }else{
            String userId = Local.login(user);
            user.setToken(userId);
            user.updateById();
            return Result.success("登录成功").setResult(userId);
        }
    }
    @PostMapping(value = "/logout/{userId}")
    @ApiOperation("退出登录")
    @ResponseBody
    public Result Logout(@ApiParam(name = "userId",value = "使用userId退出")@PathVariable String userId){
        User user = (User)Local.getSession(userId);
        user.setToken("");
        user.updateById();
        Local.logout(userId);
        return Result.success("退出成功");
    }

    @PostMapping(value = "/register/{username}/{password}")
    @ApiOperation(value = "注册接口")
    @ResponseBody
    public Result register(@ApiParam(name = "username",value = "用户名")@PathVariable String username,
                           @ApiParam(name = "password",value = "密码")@PathVariable String password){

        User user = userMapper.selectOne(new QueryWrapper<User>().eq(User.Def.USER_NMAE,username));
        if(user==null){
            user = new User(username, password);
            if(user.insert())
                return Result.success("注册成功");
            else
                return Result.error("注册失败");
        }else {
            return Result.error("已存在");
        }
    }
}
