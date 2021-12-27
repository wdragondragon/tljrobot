package com.jdragon.tljrobot.client.config;

/**
 * Create by Jdragon on 2020.01.14
 */
public class HttpAddr {
    public static String NEW_VERSION = VersionConfig.start();//最新版本编号

    private final static String SERVER_ADDR = "https://cl.tyu.wiki";

    private final static String SERVER_ADDR_NEW = "http://192.168.10.150:9527";

    private final static String ROBOT_HOME = "/robot";
    private final static String MATCH_ADDR = "/tljMatch";
    private final static String GROUP_ARTICLE = "/groupArticleCache";

    public final static String LOGIN_ADDR = SERVER_ADDR_NEW + "/account/login";
    public final static String REG_ADDR = SERVER_ADDR_NEW + "/account/register";

    public final static String ME_INFO_ADDR = SERVER_ADDR_NEW + "/account/type/info";

    public final static String ME_HISTORY_ADDR = SERVER_ADDR_NEW + "/account/type/history";

    public final static String NUM_CHANGE_NUM = SERVER_ADDR_NEW + "/account/type/changeNum";

    public final static String MATCH_GET_TODAY = SERVER_ADDR_NEW + "/account/match/today";
    public final static String MATCH_UPLOAD_TLJ_MATCH_ACH = SERVER_ADDR_NEW + "/account/match/uploadTljMatchAch";
    public final static String MATCH_GET_MATCH_ACH_BY_DATE = SERVER_ADDR_NEW + "/account/match/getPCTljMatchAchByDate";

    public final static String HISTORY_UPLOAD_HISTORY_ARTICLE = SERVER_ADDR_NEW + "/account/history/uploadHistoryAndArticle";

    public final static String HISTORY_ARTICLE = SERVER_ADDR_NEW + "/account/article/getHistoryArticle";

    public final static String GET_TLJ_NEW_VERSION = SERVER_ADDR_NEW + "/version/newVersion";

    public final static String QUERY_GROUP_MAP = SERVER_ADDR + ROBOT_HOME + "/query" + "/getGroupMap";

    public final static String GET_GROUP_ARTICLE_CACHE = SERVER_ADDR + ROBOT_HOME + GROUP_ARTICLE + "/getArticle";
    public final static String SEND_ROBOT_ARTICLE_ACH = SERVER_ADDR + ROBOT_HOME + GROUP_ARTICLE + "/sendRobotAch";

    public final static String SEND_ROBOT_ARTICLE = SERVER_ADDR + ROBOT_HOME + GROUP_ARTICLE + "/sendRobotArticle";


}
