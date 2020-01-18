package com.jdragon.tljrobot.tlj.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdragon.tljrobot.tlj.mappers.HistroyMapper;
import com.jdragon.tljrobot.tlj.pojo.History;
import com.jdragon.tljrobot.tlj.pojo.User;
import com.jdragon.tljrobot.tlj.service.UserService;
import com.jdragon.tljrobot.tljutils.Local;
import com.jdragon.tljrobot.tljutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by Jdragon on 2020.01.16
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    HistroyMapper histroyMapper;
    @Override
    public Result getUserHistory(int page,String userId) {
        PageHelper.startPage(page,20,true);
        User user = (User) Local.getSession(userId);
        List<History> histories = histroyMapper.getHistoryByUserId(user.getId());
        PageInfo<History> pageInfo = new PageInfo<>(histories);
        return Result.success("获取成功").setResult(pageInfo);
    }
}
