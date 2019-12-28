package com.jdragon.tljrobot.tlj.test;


import com.jdragon.tljrobot.tljutils.compShortCode.BetterTyping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {
    @Bean
    public BetterTyping getBetterTyping(){
        return new BetterTyping("词组提示码表");
    }
}
