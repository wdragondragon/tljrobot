package com.jdragon.tljrobot.client.test.superTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.31 09:50
 * @Description:
 */
public class Test {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        Object parameters[]=new Object[] {new B[] {new B("name1",18,"des1"),new B("name2",20,"des2")}};
//        System.out.println(parameters.getClass().getName());
//        System.out.println(parameters);
//        System.out.println(parameters[0]);
//        if(parameters instanceof Object[]){
//            System.out.println("objType");
//        }
//        if(parameters[0] instanceof B[]){
//            System.out.println("BType");
//        }
        B[] bs = new B[] {new B("name1",18,"des1"),new B("name2",20,"des2")};
        Object parameters[]=new Object[] {bs};
        Constructor constructor = C.class.getDeclaredConstructor(B[].class);
        Constructor constructor1 = C.class.getDeclaredConstructor(B.class,B.class);
        System.out.println(constructor.getName());
        C c = (C)constructor.newInstance(parameters);
        C c1 = (C)constructor1.newInstance(bs);

        Method  method = C.class.getMethod("go",String.class,String.class);
        method.invoke(c,"no","no2");
    }
}