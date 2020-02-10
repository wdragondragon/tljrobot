package com.jdragon.tljrobot.client;

import lombok.Getter;
import lombok.Setter;

/**
 * Create by Jdragon on 2020.02.08
 */
public enum Constant {
    为啥,
    或者(1),
    你好(1,"你好");
    @Getter
    @Setter
    private int i;
    @Getter
    @Setter
    private String string;
    Constant(){}
    Constant(int i){
        this.setI(i);
    }
    Constant(int i,String string){
        this.setI(i);
        this.setString(string);
    }
}
