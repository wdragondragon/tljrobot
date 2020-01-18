package com.jdragon.tljrobot.client.config;

/**
 * Create by Jdragon on 2020.01.14
 */
public class OnlineConfig {
    private final static String SERVER_ADDR = "http://localhost:8081";
//    private final static String SERVER_ADDR = "https://tlj.wiki";
    private final static String HOME_ADDR = "/home";
    private final static String ME_ADDR = "/me";
    private final static String NUM_ADDR = "/num";
    private final static String MATCH_ADDR = "/tljMatch";

    public final static String LOGIN_ADDR = SERVER_ADDR + HOME_ADDR + "/login";
    public final static String LOGOUT_ADDR = SERVER_ADDR + HOME_ADDR + "/logout";
    public final static String ME_INFO_ADDR = SERVER_ADDR + ME_ADDR + "/info";
    public final static String NUM_CHANGE_NUM = SERVER_ADDR + NUM_ADDR + "/changeNum";
    public final static String MATCH_GET_TODAY = SERVER_ADDR + MATCH_ADDR + "/today";
    public final static String MATCH_UPLOAD_ACH = SERVER_ADDR + MATCH_ADDR + "/uploadAch";
}
