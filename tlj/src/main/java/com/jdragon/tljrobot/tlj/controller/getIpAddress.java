package com.jdragon.tljrobot.tlj.controller;

import com.jdragon.tljrobot.tljutils.ipUtil.IpUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
@ApiIgnore
public class getIpAddress {
    @RequestMapping(value = "/getIp", method = RequestMethod.GET)
    public String test(HttpServletRequest request){

        //获取IP地址
        String ipAddress = IpUtil.getIpAddr(request);

        return ipAddress;
    }
}
