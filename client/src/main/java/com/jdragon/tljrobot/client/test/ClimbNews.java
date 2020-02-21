package com.jdragon.tljrobot.client.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClimbNews {

    @SneakyThrows
    public static void main(String[]args){
        List<HrefEntry> allEntryList = getAllHref();
        List<NewEntry> newEntryList = new LinkedList<>();
        for(HrefEntry hrefEntry:allEntryList) {
            newEntryList.add(getNewEntryByParentHref(hrefEntry));
        }
        File file = new File("C:\\Users\\10619\\Desktop\\新闻联播");
        if(!file.exists()){
            file.mkdir();
        }
        for (NewEntry newEntry:newEntryList){
            File newFile = new File(file,newEntry.getTitle()+".txt");
            if(!newFile.exists()){
                newFile.createNewFile();
                FileWriter fileWriter = new FileWriter(newFile,false);
                fileWriter.write(newEntry.content);
                fileWriter.flush();
                fileWriter.close();
                System.out.println("写入"+newEntry.getTitle());
            }
        }
    }
    @SneakyThrows
    private static NewEntry getNewEntryByParentHref(HrefEntry hrefEntry){
        java.net.URL url = new URL(hrefEntry.getUrl());
        URLConnection urlcon = url.openConnection();
        InputStreamReader in = new InputStreamReader(urlcon.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufferRead = new BufferedReader(in);
        String str;
        StringBuilder allStr = new StringBuilder();
        while((str=bufferRead.readLine())!=null){
            allStr.append(str);
        }
        allStr = new StringBuilder(allStr.toString().replaceAll("\\s", ""));
//        System.out.println(allStr.toString());
        String title = "";
        String regex = "<divclass=\"content-title\"><h1>(.*?)</h1>";//正则匹配出<p>与</p>之间
        Pattern pattern = Pattern.compile(regex);//匹配模式
        Matcher m = pattern.matcher(allStr.toString());//判断是否符合匹配
        while(m.find()){
            title = m.group(1);
        }
        String contentDiv = "";
        regex = "</a></strong></p>(.*?)</div><script";
        pattern = Pattern.compile(regex);
        m = pattern.matcher(allStr.toString());
        while(m.find()){
            contentDiv = m.group(1);
        }
//        System.out.println(title);
//        System.out.println(contentDiv);
        StringBuilder content = new StringBuilder();
        regex = "<p>(.*?)</p>";
        pattern = Pattern.compile(regex);
        m = pattern.matcher(contentDiv);
        while(m.find()){
            content.append(m.group(1)).append(System.lineSeparator()).append("\t");
        }
        content = new StringBuilder(content.toString().replaceAll("<strong>(.*?)</strong>",""));
//        System.out.println(content.toString());
        return new NewEntry(hrefEntry.getDate(),title,content.toString());
    }

    @Data
    @AllArgsConstructor
    public static class NewEntry{
        private String date;
        private String title;
        private String content;
    }

    private static List<HrefEntry> getAllHref(){
        List<String> parentUrlList = new LinkedList<>();
        List<HrefEntry> allEntryList = new LinkedList<>();
        for (int i = 1; i <= 4; i++) {
            if(i==1){
                parentUrlList.add("http://www.ab3.com.cn/xwlb.html");
            }else {
                parentUrlList.add("http://www.ab3.com.cn/xwlb_" + i + ".html");
            }
        }
        for (String parentUrl:parentUrlList) {
            allEntryList.addAll(getHrefByParent(parentUrl));
        }
        return allEntryList;
    }
    @SneakyThrows
    private static List<HrefEntry> getHrefByParent(String parentUrl){
        System.out.println(parentUrl);
        java.net.URL url = new URL(parentUrl);
        URLConnection urlcon = url.openConnection();
        InputStreamReader in = new InputStreamReader(urlcon.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufferRead = new BufferedReader(in);
        String str;
        StringBuilder allStr = new StringBuilder();
        while((str=bufferRead.readLine())!=null){
            allStr.append(str);
//            System.out.println(str);
        }
        String ul = "";
        String regex = "<ul class=\"newslist\">(.*?)</ul>";//正则匹配出<p>与</p>之间
        Pattern pattern = Pattern.compile(regex);//匹配模式
        Matcher m = pattern.matcher(allStr);//判断是否符合匹配
        while(m.find()){
            ul+=m.group(1);
        }

        List<String> list = new LinkedList<>();
        regex = "<li>(.*?)</li>";
        pattern = Pattern.compile(regex);
        m = pattern.matcher(ul);
        while (m.find()){
            list.add(m.group(1));
        }
//        System.out.println(list.toString());

        List<HrefEntry> hrefEntries = new LinkedList<>();
        for(String li:list){
            regex = "href=\"(.*?)\"";
            pattern = Pattern.compile(regex);
            m = pattern.matcher(li);
            String title="",date="",href="";
            while(m.find()){
                href = m.group(1);
            }
            regex = "class='atit'>(.*?)</a>";
            pattern = Pattern.compile(regex);
            m = pattern.matcher(li);
            while(m.find()){
                title = m.group(1);
            }
            regex = "<span>(.*?)</span>";
            pattern = Pattern.compile(regex);
            m = pattern.matcher(li);
            while(m.find()){
                date = m.group(1);
            }
            hrefEntries.add(new HrefEntry(href,title,date));
        }
//        System.out.println(hrefEntries.toString());
        return hrefEntries;
    }
    @Data
    @AllArgsConstructor
    public static class HrefEntry{
        private String url;
        private String title;
        private String date;
    }
}
