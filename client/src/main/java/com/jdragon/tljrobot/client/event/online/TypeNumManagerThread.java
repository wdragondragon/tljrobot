package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.common.util.Md5Utils;
import com.jdragon.tljrobot.client.config.FinalConfig;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.History;
import com.jdragon.tljrobot.client.entry.NumState;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.tljutils.HttpUtil;
import com.jdragon.tljrobot.tljutils.HttpUtils;
import com.jdragon.tljrobot.tljutils.JsonUtils;
import com.jdragon.tljrobot.tljutils.response.Result;
import com.jdragon.tljrobot.tljutils.response.table.PageTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by Jdragon on 2020.01.15
 */
public class TypeNumManagerThread extends Thread {
    private static TypeNumManagerThread typeNumManagerThread = null;

    private TypeNumManagerThread() {
    }

    public static TypeNumManagerThread getInstance() {
        if (typeNumManagerThread == null || !typeNumManagerThread.isAlive()) {
            typeNumManagerThread = new TypeNumManagerThread();
        }
        return typeNumManagerThread;
    }

    public static class NumTemp {

        public int num;

        public int rightNum;

        public int misNum;

        public int dateNum;

        public String numKey;

        public NumTemp() {
            this.num = NumState.num;
            this.rightNum = NumState.rightNum;
            this.misNum = NumState.misNum;
            this.dateNum = NumState.dateNum;
        }

        public static NumTemp createNumKey(NumTemp numTemp, NumTemp compNum) {
            NumTemp result = new NumTemp();
            result.num = numTemp.num - compNum.num;
            result.rightNum = numTemp.rightNum - compNum.rightNum;
            result.misNum = numTemp.misNum - compNum.misNum;
            result.dateNum = numTemp.dateNum - compNum.dateNum;
            result.numKey = Md5Utils.getMD5((result.num +
                    result.rightNum +
                    result.misNum +
                    result.dateNum + FinalConfig.SECRET).getBytes());
            return result;
        }
    }

    int failNum = 0;

    NumTemp compNum = new NumTemp();

    @Override
    public void run() {
        while (UserState.loginState) {
            try {
                NumTemp numTemp = new NumTemp();
                sleep(60 * 1000);
                if (this.compNum.num != numTemp.num && failNum < 60 * 24) {
                    HttpUtils httpUtils = HttpUtils.initJson();
                    httpUtils.setMethod(RequestMethod.POST);
                    httpUtils.setHeader(HttpHeaders.AUTHORIZATION, UserState.token);
                    NumTemp numKey = NumTemp.createNumKey(numTemp, compNum);
                    Class<NumTemp> numTempClass = NumTemp.class;
                    for (Field field : numTempClass.getFields()) {
                        httpUtils.setBody(field.getName(), field.get(numKey));
                    }
                    String s = httpUtils.checkExec(HttpAddr.NUM_CHANGE_NUM);
                    Result<NumTemp> result = JSONObject.parseObject(s, new TypeReference<Result<NumTemp>>() {
                    });
                    if (result.result()) {
                        failNum = 0;
                    } else {
                        failNum++;
                        this.compNum = numTemp;
                    }
                } else if (failNum >= 60 * 24) {
                    LogoutEvent.start();
                    failNum = 0;
                }
            } catch (Exception e) {
                failNum++;
                e.printStackTrace();
            }
        }
    }

    public void setLocalNumFromServer() throws Exception {
        HttpUtils httpUtils = HttpUtils.initJson();
        httpUtils.setHeader(HttpHeaders.AUTHORIZATION, UserState.token);
        httpUtils.setMethod(RequestMethod.GET);
        String s = httpUtils.checkExec(HttpAddr.ME_INFO_ADDR);
        Result<?> result = JSONObject.parseObject(s, Result.class);
        if (!result.result()) {
            throw new Exception(result.getMessage());
        }
//        JsonUtils.object2Object(result.getResult(), NumState.class);
//        JSONObject jsonObject = JSON.parseObject(HttpUtil.doPost(HttpAddr.ME_INFO_ADDR, UserState.token));
        JSONObject jsonObject = JSON.parseObject(s);
        jsonObject = jsonObject.getJSONObject(Constant.RESPONSE_RESULT);

        Class<?> clazz = NumState.class;
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields) {
            field.set(clazz, jsonObject.getIntValue(field.getName()));
        }
        this.compNum = new NumTemp();
        SwingSingleton.numberRecordLabel().setText("总:" + NumState.num +
                " 对:" + NumState.rightNum +
                " 错:" + NumState.misNum +
                " 今:" + NumState.dateNum);
    }

    public void init() {
        this.compNum = new NumTemp();
    }
}
