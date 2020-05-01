package com.jdragon.tljrobot.robot.newTyping.tools;

import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroup;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroupMemberInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.robot.newTyping.config.HttpAddr;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by Jdragon on 2020.02.05
 * @author 10619
 */
public class GroupCache {
    public static HashMap<Long,HashMap<Long,String>> groupCardCache;//群名片cache
    public static Map<Long,String> typeGroupMap = getGroupMap();
    public static ArrayList<Long> adminList;//管理员名单
    public static Map<Long,String> QQDoMap;//捐赠名单
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
                    if(card==null||card.equals("")){
                        groupMemberMap.put(qq,nickName);
                    }else {
                        groupMemberMap.put(qq,card);
                    }
                }
                groupCardCache.put(groupId,groupMemberMap);
            }
        }
        adminList = new ArrayList<>();
        QQDoMap = new HashMap<>();

        adminList.add(1061917196L);
        adminList.add(2623495687L);

        QQDoMap.put(1045865146L,"酥");
        QQDoMap.put(1157477506L,"空");
        QQDoMap.put(2963900463L,"木");
        QQDoMap.put(1816436708L,"圈");
        QQDoMap.put(2297714857L,"风");
        QQDoMap.put(2524098743L,"焕");
        QQDoMap.put(1061917196L,"谭");
        QQDoMap.put(29141847L,"白");
        QQDoMap.put(710596517L,"桂");
        QQDoMap.put(779855010L,"无");
    }

}
