package com.jdragon.tljrobot.client.constant;

import static java.io.File.separator;

/**
 * Create by Jdragon on 2020.01.12
 */
public class Constant {
    //codeTable
    public static final String CODE_TABLE = "编码文件"+separator+"输入法编码"+separator+"词组提示码表.txt";
    //typingPattern
    public static final String FOLLOW_PLAY_PATTERN = "跟打";
    public static final String WATCH_PLAY_PATTERN = "看打";
    public static final String LISTEN_PLAY_PATTERN = "听打";

    public static final String RESPONSE_MESSAGE = "message";
    public static final String RESPONSE_RESULT = "result";
    public static final String RESPONSE_CODE = "code";

    public static final int SEND_ORDER = 1;//顺序发文
    public static final int SEND_EXTRACT = 2;//随机发文
    public static final int SEND_WORDS = 3;//词语练习


    public final static String PAGE_NUM = "pageNum";
    public final static String PAGE_SIZE = "pageSize";
}