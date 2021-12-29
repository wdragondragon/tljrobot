package com.jdragon.tljrobot.client.event.online;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.common.util.Md5Utils;
import com.jdragon.tljrobot.client.api.AccountApi;
import com.jdragon.tljrobot.client.config.FinalConfig;
import com.jdragon.tljrobot.client.config.HttpAddr;
import com.jdragon.tljrobot.client.entry.NumState;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.tljutils.HttpUtils;
import com.jdragon.tljrobot.tljutils.response.Result;
import com.jdragon.tljrobot.tljutils.zFeign.DynaProxyHttp;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Field;

/**
 * Create by Jdragon on 2020.01.15
 */
public class TypeNumManagerThread extends Thread {

    private final AccountApi accountApi = DynaProxyHttp.getInstance(AccountApi.class);

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
                Thread.sleep(60 * 1000);
                if (this.compNum.num != numTemp.num && failNum < 60 * 24) {
                    NumTemp numKey = NumTemp.createNumKey(numTemp, compNum);
                    NumTemp result = accountApi.changeNum(numKey);
                    if (result != null) {
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
        JSONObject typeInfo = accountApi.getMyInfo();
        Class<?> clazz = NumState.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.set(clazz, typeInfo.getIntValue(field.getName()));
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
