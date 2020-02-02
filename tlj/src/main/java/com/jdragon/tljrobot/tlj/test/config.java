package com.jdragon.tljrobot.tlj.test;


import com.jdragon.tljrobot.tljutils.compShortCode.BetterTyping;
import com.jdragon.tljrobot.tljutils.compShortCode.simpleEntry.SimpleEntry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.io.File.separator;

@Configuration
public class config {
    @Bean
    public BetterTyping getBetterTyping(){
        return new BetterTyping("编码文件"+separator+"输入法编码"+separator+"词组提示码表.txt");
    }

    @Bean
    public SimpleEntry getSimpleEntry(){
        return new SimpleEntry();
    }
}
