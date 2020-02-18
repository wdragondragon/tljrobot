package com.jdragon.tljrobot.client.utils.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by Jdragon on 2020.01.13
 */
public class DoCheck {
    public static String buildCheckStr(String str, String model){
        char[] d = str.toCharArray();
        char[] c = model.toCharArray();
        for (int i = 0; i < d.length; i++) {
            d[i] = (char) (d[i] ^ c[i%5]);
        }
        return new String(d);
    }
    public static boolean check(String str, String model){
        String []regex = {"速度(.*?) ","击键(.*?) ","码长(.*?) "};
        double a=0.0;
        for (String s : regex) {
            Pattern pattern = Pattern.compile(s);//匹配模式
            Matcher m = pattern.matcher(str);//判断是否符合匹配
            if (m.find()) {
                String temp = m.group(1);
                if (temp.contains("/")) {
                    temp = temp.split("/")[0];
                }
                a += Double.parseDouble(String.format("%.2f", Double.parseDouble((temp))));

            }
        }
        String Check = str.substring(str.indexOf("校验")+2);
        String temp = String.format("%.2f", a);
        String check = buildCheckStr(temp,model);
        return check.equals(Check);
    }
}
