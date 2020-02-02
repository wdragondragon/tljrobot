package com.jdragon.tljrobot.robot.newTyping.config;

/**
 * Create by Jdragon on 2020.01.21
 */
public class HttpAddr {
    private final static String SERVER_ADDR = "http://localhost:8081";

    private final static String ROBOT_HOME = "/robot";

    private final static String UNION_ADDR = "/unionMatch";

    public final static String UPLOAD_HISTORY = SERVER_ADDR + ROBOT_HOME + "/uploadHistory";

    public final static String UPLOAD_UNION_MATCH = SERVER_ADDR + ROBOT_HOME + UNION_ADDR + "/uploadUnionMatch";

    public final static String GET_UNION_MATCH = SERVER_ADDR + ROBOT_HOME + UNION_ADDR + "/getUnionMatch";

    public final static String GET_UNION_MATCH_RANK = SERVER_ADDR + ROBOT_HOME + UNION_ADDR + "/getUnionAchRank";
}
