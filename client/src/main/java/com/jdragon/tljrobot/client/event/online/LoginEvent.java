package com.jdragon.tljrobot.client.event.online;

import com.jdragon.tljrobot.client.api.AccountApi;
import com.jdragon.tljrobot.client.api.body.AccountDto;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.tljutils.zFeign.DynaProxyHttp;
import org.springframework.http.HttpHeaders;

/**
 * Create by Jdragon on 2020.01.14
 */
public class LoginEvent {

    private static final AccountApi accountApi = DynaProxyHttp.getInstance(AccountApi.class);

    public static String start(String username, String password) {
        try {
            AccountDto accountDto = new AccountDto(username, password);
            String token = accountApi.login(accountDto);
            UserState.loginState = true;
            UserState.token = "Bearer " + token;
            DynaProxyHttp.addGlobalHeader(HttpHeaders.AUTHORIZATION, UserState.token);
            TypeNumManagerThread.getInstance().setLocalNumFromServer();
            TypeNumManagerThread.getInstance().start();
            new KeepALiveThread().start();
            return "登录成功";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}