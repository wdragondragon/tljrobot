package com.jdragon.tljrobot.client.test;

import org.junit.jupiter.api.Test;

/**
 * Create by Jdragon on 2020.02.08
 */
public class TestEnum {
    @Test
    public void test(){
        System.out.println(Constant.为啥+":"+Constant.为啥.getI()+":"+Constant.为啥.getString());
        System.out.println(Constant.或者+":"+Constant.或者.getI()+":"+Constant.或者.getString());
        System.out.println(Constant.你好+":"+Constant.你好.getI()+":"+Constant.你好.getString());
    }
}
