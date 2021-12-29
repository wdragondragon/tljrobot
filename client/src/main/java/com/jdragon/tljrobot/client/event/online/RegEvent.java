package com.jdragon.tljrobot.client.event.online;

import com.jdragon.tljrobot.client.api.AccountApi;
import com.jdragon.tljrobot.client.api.body.AccountDto;
import com.jdragon.tljrobot.tljutils.zFeign.DynaProxyHttp;
import lombok.SneakyThrows;

/**
 * Create by Jdragon on 2020.02.02
 */
public class RegEvent {

    private static final AccountApi accountApi = DynaProxyHttp.getInstance(AccountApi.class);

    @SneakyThrows
    public static String start(String username, String password) {
        AccountDto accountDto = new AccountDto(username,password);
        return accountApi.register(accountDto);
    }
}