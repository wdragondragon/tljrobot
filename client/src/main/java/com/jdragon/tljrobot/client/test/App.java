package com.jdragon.tljrobot.client.test;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.24 12:54
 * @Description:
 */
public class App
{

    static int fun1(int n, int k) {
        // TODO Auto-generated method stub
        if(n==1||k==1) {
            System.out.println("1");
            return 1;
        }
        else if(n<k) {
            return(fun1(n,n));
        }
        else if(n==k) {
            return(1+fun1(n,k-1));
        }
        else{

            return(fun1(n,k-1)+fun1(n-k,k));
        }
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println(fun1(5,4));
//        System.out.println(fun1(50,40));
    }
}