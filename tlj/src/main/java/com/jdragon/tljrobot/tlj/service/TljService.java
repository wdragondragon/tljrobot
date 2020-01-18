package com.jdragon.tljrobot.tlj.service;

/**
 * Create by Jdragon on 2020.01.18
 */
public interface TljService {
    public boolean checkNumSecret(int num,
                                  int rightNum, int misNum,
                                  int dateNum, String numKey);

    public boolean changeNum(String userId,int num, int rightNum, int misNum, int dateNum);
}
