package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdragon.tljrobot.client.config.OnlineConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.NumState;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by Jdragon on 2020.01.15
 */
public class TypeNumManager extends Thread {
    private static TypeNumManager typeNumManager = null;
    private TypeNumManager(){}
    public static TypeNumManager getInstance() {
        if(typeNumManager==null||!typeNumManager.isAlive())typeNumManager = new TypeNumManager();
        return typeNumManager;
    }
    int numTemp;
    public void run() {
        while(UserState.loginState) {
            try {
                if (numTemp != NumState.num) {
                    Map<String,String> params = new HashMap<>();
                    Class clazz = NumState.class;
                    Field[] fields = clazz.getDeclaredFields();
                    for(Field field:fields){
                        params.put(field.getName(), String.valueOf(field.get(clazz)));
                    }
                    params.put("numKey", NumState.getNumKey());
                    String resultJsonStr = HttpUtil.doGetParam(OnlineConfig.NUM_CHANGE_NUM ,params,UserState.token);
                    JSONObject resultJson = JSONObject.parseObject(resultJsonStr);
                    if(resultJson.getString(Constant.RESPONSE_MESSAGE).equals("更新成功")){
                        numTemp = NumState.num;
                    }
                }
                sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @SneakyThrows
    public void setLocalNumFromServer(){
        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(OnlineConfig.ME_INFO_ADDR, UserState.token));
        jsonObject = jsonObject.getJSONObject(Constant.RESPONSE_RESULT);
        Class clazz = NumState.class;
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields) {
            field.set(clazz, jsonObject.getIntValue(field.getName()));
        }
        numTemp = NumState.num;
        SwingSingleton.NumberRecordLabel().setText("总:" + NumState.num +
                " 对:" + NumState.rightNum +
                " 错:" + NumState.misNum +
                " 今:" + NumState.dateNum);
    }
}
