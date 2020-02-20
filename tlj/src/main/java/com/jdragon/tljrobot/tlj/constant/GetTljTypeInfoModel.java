package com.jdragon.tljrobot.tlj.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum GetTljTypeInfoModel {
    全部成绩(0),赛文成绩(1),生稿成绩(2);
    @Getter
    private int Model;
}
