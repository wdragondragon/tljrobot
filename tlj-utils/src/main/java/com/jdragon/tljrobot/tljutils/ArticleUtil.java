package com.jdragon.tljrobot.tljutils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by Jdragon on 2020.01.19
 */
public class ArticleUtil {
    public static String getRandomContent(){
        String str = getUrlConStr();//获得网站str
        String regex = "<p>(.*?)</p>";//正则匹配出<p>与</p>之间
        Pattern pattern = Pattern.compile(regex);//匹配模式
        Matcher m = pattern.matcher(str);//判断是否符合匹配
        String str1 = "";
        while(m.find()){
            int i =1;
            str1+=m.group(i);
            i++;
        }
        clearSpace(str1);
        if(str1.length()>600) {
            str1 = str1.substring(0,599);
        }
        str1 = replace(str1);
//			System.out.println(str1);
        return str1;
    }
    public static String getRandomContent2(){
        String str = getUrlConStr();//获得网站str
        String regex = "<p>(.*?)</p>";//正则匹配出<p>与</p>之间
        Pattern pattern = Pattern.compile(regex);//匹配模式
        Matcher m = pattern.matcher(str);//判断是否符合匹配
        String str1 = "";
        while(m.find()){
            int i =1;
            str1+=m.group(i);
            i++;
        }
        clearSpace(str1);
        str1 = replace(str1);
//			System.out.println(str1);
        return str1;
    }
    static String getUrlConStr(){
        try{
            URL url = new URL("https://meiriyiwen.com/random");
            URLConnection urlcon = url.openConnection(); //模拟浏览器发出请求
//			urlcon.setRequestProperty("User-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
            urlcon.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
            InputStreamReader in = new InputStreamReader(urlcon.getInputStream(),"utf-8");
            BufferedReader bufferRead = new BufferedReader(in);
            String str = "";
            String temp;
            while((temp = bufferRead.readLine())!=null) {
                str+=temp;
            }
//			System.out.println(str);
            return str;
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JTextArea(),"获取失败,请检查网络");
        }
        return "";
    }
    public static String replace(String str){
        String initChar = ";:,.!?()";
        String afterChar = "；：，。！？（）";
        char[] a = str.toCharArray();
        int b ;
        char[] y = afterChar.toCharArray();
        for(int i =0;i<a.length;i++) {
            if((b = initChar.indexOf(a[i]))!=-1) {
                a[i] = y[b];
            }
        }
        str = String.valueOf(a);
        return str;
    }
    public static String quotationMarkReplacement(String str){
        return str.replaceAll("[“”]","\"");
    }
    public static String clearSpace(String str){
        str = str.replaceAll("\\s", "");
        str = str.replaceAll("　","");
        return str;
    }
}
