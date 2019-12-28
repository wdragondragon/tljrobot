package com.jdragon.tljrobot.tlj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/")
public class goHome {
    @RequestMapping("/")
    public String goHome(){
        return "home";
    }


}
