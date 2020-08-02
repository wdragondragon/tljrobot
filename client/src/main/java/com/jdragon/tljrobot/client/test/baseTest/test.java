package com.jdragon.tljrobot.client.test.baseTest;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.14 08:47
 * @Description:
 */
public class test {
    public static void main(String[] args) {
        int i = Integer.MIN_VALUE;
        long j = Integer.MIN_VALUE;
        System.out.println(i==j);

        String abc = "abc";
        String abc2 = "ab"+"c";
        System.out.println(abc==abc2);

        String a = "a";
        String b = "b";
        String ab = "ab";
        System.out.println((a+b)==ab);

        double a1 = 1.1;
        a1++;
        a1 = a1%a1;
        a1 = a1/a1;
        a1 += a1;
    }
}
