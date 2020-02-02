package com.jdragon.tljrobot.tlj.scheduling;

import com.jdragon.tljrobot.tlj.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Create by Jdragon on 2020.02.02
 */
@Component
public class DateNumInit {
    @Autowired
    UserMapper userMapper;
    @Scheduled(cron = "0 0 0 */1 * ?")
    public void scheduled(){
        userMapper.dateNumInit();
    }
}
