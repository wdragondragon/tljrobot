package com.jdragon.tljrobot.client.utils.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.tljutils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 10619
 */
public class NetArticleTools {
    public static HashMap<Long,String> typeGroupMap = null;
    public static HashMap<Long,String> getGroupMap(){
        if(typeGroupMap!=null) {
            return typeGroupMap;
        }
        HashMap<Long,String> hashMap = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.doPost(HttpAddr.QUERY_GROUP_MAP));
        String retMessage = jsonObject.getString("message");
        if(retMessage.equals("获取成功")) {
            JSONArray result = jsonObject.getJSONArray("result");
            for (Object object:result){
                JSONObject groupJson = (JSONObject)object;
                hashMap.put(groupJson.getLong("groupId"),groupJson.getString("groupName"));
            }
        }
        return typeGroupMap=hashMap;
    }
    public static boolean getArticle(){
        HashMap<String,String > postParams = new HashMap<>();
        postParams.put("groupId", String.valueOf(getSelectGroupId()));
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.doPostParams(HttpAddr.GET_GROUP_ARTICLE_CACHE,postParams));
        String retMessage = jsonObject.getString("message");
        if("获取成功".equals(retMessage)){
            JSONObject articleJson = jsonObject.getJSONObject("result");
            Article.getArticleSingleton(articleJson.getIntValue("id"),articleJson.getString("title"),articleJson.getString("content"));
            return true;
        }
        return false;
    }
    public static long getSelectGroupId(){
        for(Map.Entry<Long,String> entry:getGroupMap().entrySet()){
            System.out.println(entry.getValue());
            if(entry.getValue().equals(SwingSingleton.qQNameLabel().getText())){
                System.out.println(entry.getKey());
                return entry.getKey();
            }
        }
        return 0L;
    }
}
