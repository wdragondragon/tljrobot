package com.jdragon.tljrobot.tlj.service;

import com.jdragon.tljrobot.tlj.pojo.Article;
import com.jdragon.tljrobot.tlj.pojo.History;
import com.jdragon.tljrobot.tlj.pojo.TljMatch;

/**
 * Create by Jdragon on 2020.01.18
 */
public interface TljService {
    public boolean checkNumSecret(int num,
                                  int rightNum, int misNum,
                                  int dateNum, String numKey);

    public boolean changeNum(String userId,int num, int rightNum, int misNum, int dateNum);

    public TljMatch insertTljMatch();

    public boolean uploadTljMatchAch(int userId, History history);

    public boolean uploadHistory(int userId, History history, Article article);
}
