package com.jdragon.tljrobot.robot.typing;

import com.jdragon.tljrobot.tljutils.HttpUtil;
import org.junit.Test;

public class test {
    @Test
    public void test(){
        System.out.println(HttpUtil.doGet("https://www.breathcoder.cn/jihuo"));

    }
}
