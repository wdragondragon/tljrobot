package com.jdragon.tljrobot.tlj.controller;

import com.jdragon.tljrobot.tljutils.ipUtil.IpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
@ApiIgnore
public class getIpAddress {
    @GetMapping(value = "/getIp")
    public String test(HttpServletRequest request){

        //获取IP地址

        return IpUtil.getIpAddr(request);
    }
}
