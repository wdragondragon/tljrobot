package com.jdragon.tljrobot.robot.typing;

import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.tljutils.compShortCode.BetterTyping;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class test {
    @Test
    public void test(){
        String str = "/ds/df.js";
        List allow2 = Arrays.asList("img","css","js","png","jpg","txt");
        System.out.println(str.substring(str.lastIndexOf(".")+1));
    }
}
