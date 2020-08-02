package com.jdragon.tljrobot.client.test.superTest;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.31 09:51
 * @Description:
 */
public class C {
    public C(B... bs){
        for(B b:bs){
            System.out.println(b.getName());
        }
        System.out.println("构造C");
    }
    public C(B b1,B b2){
        System.out.println("非可变构造器");
    }
    public void go(String str1,String str2){
        System.out.println(str1+str2);
    }

}
