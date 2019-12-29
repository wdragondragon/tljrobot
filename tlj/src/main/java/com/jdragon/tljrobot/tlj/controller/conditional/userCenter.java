package com.jdragon.tljrobot.tlj.controller.conditional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdragon.tljrobot.tlj.mappers.HistroyMapper;
import com.jdragon.tljrobot.tlj.pojo.History;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/me")
@Api(tags = "个人信息（需登录）")
public class userCenter {
    @PostMapping(value = "/info/{userId}")
    @ApiOperation("获取个人信息")
    @ResponseBody
    public Result myInfo(@ApiParam(name = "userId",value = "使用userId获取") @PathVariable String userId){
        return Result.success("获取成功").setResult(Local.getSession(userId));
    }
    @Autowired
    HistroyMapper histroyMapper;
    @PostMapping(value = "/history/{page}/{userId}")
    @ResponseBody
    public Result getHistory(@ApiParam(name = "page",value = "获取的页数")@PathVariable int page,
                             @ApiParam(name = "userId",value = "使用userId获取")@PathVariable String userId){
        PageHelper.startPage(page,20,true);
        User user = (User)Local.getSession(userId);
        List<History> histories = histroyMapper.getHistoryByName(user.getName());
        PageInfo<History> pageInfo = new PageInfo<History>(histories);
        return Result.success("获取成功").setResult(pageInfo);
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
