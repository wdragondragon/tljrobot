package com.jdragon.tljrobot.tlj.service;

import com.jdragon.tljrobot.tljutils.Result;

/**
 * Create by Jdragon on 2020.01.16
 */
public interface UserService {
    public Result getUserHistory(int page,String userId);
}
