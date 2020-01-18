package com.jdragon.tljrobot.tlj.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdragon.tljrobot.tlj.mappers.UserMapper;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tljutils.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Create by Jdragon on 2020.01.17
 */
@Component
public class KeepUserState implements ApplicationRunner {
    @Autowired
    UserMapper userMapper;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> userList = userMapper.selectList(new QueryWrapper<>());
        for(User user:userList){
            if(!user.getToken().equals("")){
                Local.login(user.getToken(),user);
            }
        }
//        timer();
    }
//    public void timer(){
//        Timer timer=new Timer();
//        timer.schedule(new java.util.TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        },1000,1000);
//    }
}
