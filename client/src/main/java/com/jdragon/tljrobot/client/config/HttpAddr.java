package com.jdragon.tljrobot.client.config;

/**
 * Create by Jdragon on 2020.01.14
 */
public class HttpAddr {
    public static String NEW_VERSION = VersionConfig.start();//最新版本编号

    private final static String SERVER_ADDR = "https://cl.tyu.wiki";

//    public final static String SERVER_ADDR_NEW = "http://192.168.10.150:9527";
    public final static String SERVER_ADDR_NEW = "https://clgateway.tyu.wiki";

    private final static String ROBOT_HOME = "/robot";
    private final static String GROUP_ARTICLE = "/groupArticleCache";

    public final static String QUERY_GROUP_MAP = SERVER_ADDR + ROBOT_HOME + "/query" + "/getGroupMap";

    public final static String GET_GROUP_ARTICLE_CACHE = SERVER_ADDR + ROBOT_HOME + GROUP_ARTICLE + "/getArticle";
    public final static String SEND_ROBOT_ARTICLE_ACH = SERVER_ADDR + ROBOT_HOME + GROUP_ARTICLE + "/sendRobotAch";

    public final static String SEND_ROBOT_ARTICLE = SERVER_ADDR + ROBOT_HOME + GROUP_ARTICLE + "/sendRobotArticle";


}
