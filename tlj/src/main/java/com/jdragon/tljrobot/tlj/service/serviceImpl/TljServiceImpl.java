package com.jdragon.tljrobot.tlj.service.serviceImpl;

import com.alibaba.nacos.common.util.Md5Utils;
import com.jdragon.tljrobot.tlj.mappers.UserMapper;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tlj.service.TljService;
import com.jdragon.tljrobot.tljutils.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * Create by Jdragon on 2020.01.18
 */
@PropertySource("classpath:application.yml")
@Service
public class TljServiceImpl implements TljService {
    @Value("${server.secret}")
    String secret;

    @Autowired
    UserMapper userMapper;
    @Override
    public boolean checkNumSecret(int num, int rightNum, int misNum, int dateNum, String numKey) {
        if(!Md5Utils.getMD5((num+rightNum+misNum+dateNum+secret).getBytes()).equals(numKey)){
            return false;
        }
        else return true;
    }

    @Override
    public boolean changeNum(String userId,int num, int rightNum, int misNum, int dateNum) {
        User user = (User) Local.getSession(userId);
        user.setNum(num);
        user.setRightNum(rightNum);
        user.setMisNum(misNum);
        user.setDateNum(dateNum);
        int i = userMapper.updateById(user);
        if(i>0)return true;
        else return false;
    }

}
