package com.jdragon.tljrobot.tlj.controller.conditional;

import com.jdragon.tljrobot.tlj.service.UserService;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/me")
@Api(tags = "个人信息（需登录）")
public class UserCenterController {
    @Autowired
    UserService userService;
    @PostMapping(value = "/info/{userId}")
    @ApiOperation("获取个人信息")
    @ResponseBody
    public Result getMyInfo(@ApiParam(name = "userId",value = "使用userId获取") @PathVariable String userId){
        return Result.success("获取成功").setResult(Local.getSession(userId));
    }

    @PostMapping(value = "/history/{page}/{userId}")
    @ResponseBody
    public Result getHistory(@ApiParam(name = "page",value = "获取的页数")@PathVariable int page,
                             @ApiParam(name = "userId",value = "使用userId获取")@PathVariable String userId){

        return userService.getUserHistory(page,userId);
    }
    @GetMapping("/history/{userId}")
    public String getHistoryTable(@PathVariable String userId){
        return "myAllHistory";
    }
    @GetMapping("/info/{userId}" )
    public String getInfo(@PathVariable String userId){
        return "myInfo";
    }
}
