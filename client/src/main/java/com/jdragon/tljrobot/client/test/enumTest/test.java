package com.jdragon.tljrobot.client.test.enumTest;

import org.junit.jupiter.api.Test;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.20 20:34
 * @Description:
 */
public class test {
    @Test
    public void test() throws ClassNotFoundException {
        Class classs = Class.forName("com.jdragon.tljrobot.client.test.enumTest.CodeInfoEnum");
        System.out.println(classs.getName());
    }
}
