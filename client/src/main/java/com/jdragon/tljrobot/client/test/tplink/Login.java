package com.jdragon.tljrobot.client.test.tplink;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.05.03 14:19
 * @Description:
 */
public class Login {
    public static String orgAuthPwd(String a) {
        return securityEncode("RDpbLfCPsJZ7fiv",
                a
                , "yLwVl0zKqws7LgKPRQ84Mdt708T1qQ3Ha7xv3H7NyU84p21BriUWBU43odz3iP4rBL3cD02KZciXTysVXiV8ngg6vL48rPJyAUw0HurW20xqxv9aYb4M9wK1Ae0wlro510qXeU07kV57fQMc8L6aLgMLwygtc0F10a0Dg70TOoouyFhdysuRMO51yY5ZlOZZLEal1h0t9YQW0Ko7oBwmCAHoic4HYbUyVeU3sfQ1xtXcPcf1aT303wAQhv66qzW");
    }

    public static String securityEncode(String a, String b, String c) {
        StringBuilder e = new StringBuilder();
        int f, g, h, k, l, n;
        g = a.length();//短验证码的长度(固定为15)
        h = b.length();//密码长度
        k = c.length();//长验证码的长度(固定为255)
        //将短验证码字符串和密码字符串的长度进行比较
        f = Math.max(g, h);
        //f取长的那个字符串的长度
        for (int p = 0; p < f; p++) {
            n = l = 187;
            if (p >= g) {
                n = b.charAt(p);//n取密码中的以0开始的第p位字符
            } else {
                if (p >= h) {
                    l = a.charAt(p);//l取短验证码中的以0开始的第p位字符
                } else {
                    l = a.charAt(p);//l取短验证码中的以0开始的第p位字符
                    n = b.charAt(p);//n取密码中的以0开始的第p位字符
                }
            }
            //每次计算出l和n的值之后对其取异或然后除以k(也就是除以255),
            //取长验证码中以0开始的第(l^n)%k位，然后拼接到字符串e的后面
            e.append(c.charAt((l ^ n) % k));
        }

        return e.toString();//返回加密后的密码
    }

    public static void main(String[] args) throws InterruptedException {
        String urlStr = "http://192.168.1.1:80/";
        String dsUrlStr = "http://192.168.1.1:80/stok=";

        String loginBody = "{\"method\":\"do\",\"login\":{\"password\":\""+orgAuthPwd("951753")+"\"}}";
        JSONObject loginResult = JSON.parseObject(requestMethod(HTTP_POST,urlStr,loginBody));
        String stok = loginResult.getString("stok");
        while (true) {
            System.out.println("==========hostInfo=========");
            String dsBody = "{\"hosts_info\":{\"table\":\"online_host\"},\"method\":\"get\"}";
            JSONObject dsJsonObject = JSON.parseObject(requestMethod(HTTP_POST, dsUrlStr + stok, dsBody));
            JSONArray hostsInfo = dsJsonObject.getJSONObject("hosts_info").getJSONArray("online_host");

            List<HostInfo> hostInfoList = new ArrayList<>();
            for (Object o : hostsInfo) {
                JSONObject hostInfoJSON = (JSONObject) o;
                for (Object oIn : hostInfoJSON.values()) {
                    JSONObject hostInfoValueJSON = (JSONObject) oIn;
                    HostInfo hostInfo = JSONObject.toJavaObject(hostInfoValueJSON, HostInfo.class);
                    hostInfoList.add(hostInfo);
                }
            }

            for(HostInfo hostInfo:hostInfoList){
                System.out.println(hostInfo);
            }

            Thread.sleep(1000);
        }
    }
    // post请求
    public static final String HTTP_POST = "POST";

    // get请求
    public static final String HTTP_GET = "GET";

    // utf-8字符编码
    public static final String CHARSET_UTF_8 = "utf-8";

    // HTTP内容类型。如果未指定ContentType，默认为TEXT/HTML
    public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";

    // HTTP内容类型。相当于form表单的形式，提交暑假
    public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";

    //
    public static final String CONTENT_TYPE_JSON= "application/json";
    // 请求超时时间
    public static final int SEND_REQUEST_TIME_OUT = 50000;

    // 将读超时时间
    public static final int READ_TIME_OUT = 50000;
    public static String requestMethod(String requestType, String urlStr, String body) {

        // 是否有http正文提交
        boolean isDoInput = false;
        if (body != null && body.length() > 0)
            isDoInput = true;
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuilder resultBuffer = new StringBuilder();
        String tempLine;
        try {
            // 统一资源
            URL url = new URL(urlStr);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
            // http正文内，因此需要设为true, 默认情况下是false;
            if (isDoInput) {
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            }
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
            // 设置一个指定的超时值（以毫秒为单位）
            httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
            // 将读超时设置为指定的超时，以毫秒为单位。
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            // 设置字符编码
            httpURLConnection.setRequestProperty("Accept-Charset", CHARSET_UTF_8);
            // 设置内容类型
            httpURLConnection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod(requestType);

            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            // 如果在已打开连接（此时 connected 字段的值为 true）的情况下调用 connect 方法，则忽略该调用。
            httpURLConnection.connect();

            if (isDoInput) {
                outputStream = httpURLConnection.getOutputStream();
                outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(body);
                outputStreamWriter.flush();// 刷新
            }
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);

                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {// 关闭流

            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            return URLDecoder.decode(resultBuffer.toString(),CHARSET_UTF_8);
        } catch (UnsupportedEncodingException e) {
            return resultBuffer.toString();
        }
    }
}
