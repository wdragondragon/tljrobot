package com.jdragon.tljrobot.robot.typing;

import com.alibaba.nacos.common.util.Md5Utils;
import org.junit.Test;

public class test {
    @Test
    public void test(){
        System.out.println(Md5Utils.getMD5("12345678".getBytes()));
    }
}
