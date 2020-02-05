package com.jdragon.tljrobot.robot.newTyping;

import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroup;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroupMemberInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.robot.newTyping.config.HttpAddr;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by Jdragon on 2020.02.05
 */
public class GroupCache {
    public static  HashMap<Long,HashMap<Long,String>> groupCardCache;//群名片cache
    public static Map<Long,String> typeGroupMap = getGroupMap();
    @Test
    public void test(){

    }
    private static Map<Long,String> getGroupMap(){
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
        return hashMap;
    }
    public static void refreshCardCache(IcqHttpApi icqHttpApi){
        groupCardCache = new HashMap<>();
        for(RGroup rGroup:icqHttpApi.getGroupList().getData()){
            long groupId = rGroup.getGroupId();
            if(typeGroupMap.containsKey(groupId)){
                HashMap<Long,String> groupMemberMap = new HashMap<>();
                List<RGroupMemberInfo> groupMemberInfoList = icqHttpApi.getGroupMemberList(groupId).getData();
                for(RGroupMemberInfo rGroupMemberInfo:groupMemberInfoList){
                    long qq = rGroupMemberInfo.getUserId();
                    String card = rGroupMemberInfo.getCard();
                    String nickName = rGroupMemberInfo.getNickname();
                    if(card==null||card==""){
                        groupMemberMap.put(qq,nickName);
                    }else groupMemberMap.put(qq,card);
                }
                groupCardCache.put(groupId,groupMemberMap);
            }
        }
    }
}
