package com.jdragon.tljrobot.tljutils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by Jdragon on 2020.01.19
 */
public class ArticleUtil {
    public static String getRandomContent() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = simpleDateFormat.format(date);
        long nowTime = date.getTime() / 1000;
        String randomArticleUrl = System.getProperty("randomArticleUrl");
        if (randomArticleUrl == null) {
            throw new RuntimeException("randomArticleUrl is null");
        }
        String url = randomArticleUrl + "?from=" + dateStr + "&to=" + dateStr + "&updated_at=" + nowTime;
        String str1 = getUrlConStr(url);//获得网站str
        JSONArray jsonArray = JSONArray.parseArray(str1);
        String article = jsonArray.getJSONObject(0).getString("article");
        article = article.replaceAll("[\n「」]", "");

        clearSpace(article);
        if (article.length() > 600) {
            article = article.substring(0, 599);
        }
        article = replace(article);
//			System.out.println(str1);
        return article;
    }

    public static String getRandomContent2() {
        return "随机一文维护中";
    }

    public static String getUrlConStr(String urlStr) {
        try {
//            URL url = new URL("https://meiriyiwen.com/random");
            URL url = new URL(urlStr);
            URLConnection urlcon = url.openConnection(); //模拟浏览器发出请求
//			urlcon.setRequestProperty("User-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
            urlcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
            InputStreamReader in = new InputStreamReader(urlcon.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader bufferRead = new BufferedReader(in);
            StringBuilder str = new StringBuilder();
            String temp;
            while ((temp = bufferRead.readLine()) != null) {
                str.append(temp);
            }
//			System.out.println(str);
            return str.toString();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JTextArea(), "获取失败,请检查网络");
        }
        return "";
    }

    public static void main(String[] args) {
        String urlConStr = getUrlConStr("https://judouapp.com/api/v2/posts/random?app_key=af66b896-665e-415c-a119-6ca5233a6963&channel=App%20Store&device_id=e02d9f018e8c5db08965cc7c0de471c3&device_type=iPhone14%2C3&newbie=1&platform=ios&signature=66b8548e745fdef185ac4e3d90f24380&system_version=15.5&timestamp=1685467684&version=4.2.6&version_code=91");
        JSONObject jsonObject = JSONObject.parseObject(urlConStr);
        String shareUrl = jsonObject.getString("share_url");
        String urlConStr2 = getUrlConStr(shareUrl);
        String regex = "<div class='flex flex-col mt-4 text-gray-700 leading-7 text-justify'><p>(.*?)</p></div>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(urlConStr2);
        if (matcher.find()) {
            String group = matcher.group(1);
            group = group.replaceAll("<br>|</br>|「|」|<p>|</p>", "");
            System.out.println(group);
        }
    }

    public static String replace(String str) {
        String initChar = ";:,.!?()";
        String afterChar = "；：，。！？（）";
        char[] a = str.toCharArray();
        int b;
        char[] y = afterChar.toCharArray();
        for (int i = 0; i < a.length; i++) {
            if ((b = initChar.indexOf(a[i])) != -1) {
                a[i] = y[b];
            }
        }
        str = String.valueOf(a);
        return str;
    }

    public static String quotationMarkReplacement(String str) {
        return str.replaceAll("[“”]", "\"");
    }

    public static String clearSpace(String str) {
        str = str.replaceAll("\\s", "");
        str = str.replaceAll("　", "");
        return str;
    }

    public static String leaveOneSpace(String str) {
        str = str.replaceAll("\\s+", " ");
        if (str.startsWith(" ")) {
            str = str.substring(1);
        }
        return str;
    }
}
