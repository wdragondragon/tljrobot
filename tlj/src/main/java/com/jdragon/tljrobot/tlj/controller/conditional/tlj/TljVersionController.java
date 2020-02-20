package com.jdragon.tljrobot.tlj.controller.conditional.tlj;

import com.jdragon.tljrobot.tlj.mappers.TljVersionMapper;
import com.jdragon.tljrobot.tljutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by Jdragon on 2020.02.07
 */
@RestController
@RequestMapping("/tljVersion")
@Api(tags = "拖拉机版本")
public class TljVersionController {
    @Autowired
    TljVersionMapper tljVersionMapper;
    @ApiOperation("获取最新版本号")
    @PostMapping("/getNewVersion")
    public Result getNewVersion(){
        return Result.success("获取成功").setResult(tljVersionMapper.selectNewVersion());
    }
}
