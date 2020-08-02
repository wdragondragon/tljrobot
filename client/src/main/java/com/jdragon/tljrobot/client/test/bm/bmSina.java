package com.jdragon.tljrobot.client.test.bm;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.06.08 14:54
 * @Description:
 */
public class bmSina {
    @Test
    public void test() {
        String PaaSToken="kLxLJcqASI5leYHRJC8OfiqqW2QiLVII";
        String PaaSID="zdxm";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long epoch = 0;
        Date d = new Date();
        String t = df.format(d);
        try {
            epoch = df.parse(t).getTime() / 1000;// unix时间戳
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String nonce = UUID.randomUUID().toString();

// 计算签名
        String api_signature = EncriptUtils.getSHA256StrJava(epoch + PaaSToken + nonce + epoch);
        Map<String, Map> map = new HashMap<>();
        Map<String,Map> data = null;
        if (data == null)
            data = map;
        Map<String,String> temp = new HashMap<>();
        temp.put("id","");
        data.put("query",temp);
        StringEntity entity = new StringEntity(JSON.toJSONString(data), "UTF-8");
        URI url = null;
        try {
            url = new URI("http://10.198.246.32/ebus/gzshpqsjfwpt/v1/service/yjsxspbcxjk");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpPost httppost = new HttpPost(url);
        System.out.println(api_signature);
        httppost.addHeader("accept", "application/json");
        httppost.addHeader("Charset", "UTF-8");
        httppost.addHeader("Content-Type", "application/json; charset=UTF-8");
        httppost.addHeader("x-tif-paasid", PaaSID);
        httppost.addHeader("x-tif-signature", api_signature.toUpperCase());
        httppost.addHeader("x-tif-timestamp", String.valueOf(epoch));
        httppost.addHeader("x-tif-nonce", nonce);
        httppost.setEntity(entity);
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity entity1 = response.getEntity();
            String result = EntityUtils.toString(entity1, "UTF-8");
            System.out.println(result);
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
