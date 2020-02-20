package com.jdragon.tljrobot.tlj.controller;

import com.jdragon.tljrobot.tljutils.ipUtil.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
@Api(tags = "IP操作")
public class getIpAddress {
    @GetMapping(value = "/getIp")
    @ApiOperation("获取浏览器端ip地址")
    public String test(HttpServletRequest request){

        //获取IP地址

        return IpUtil.getIpAddr(request);
    }
}
