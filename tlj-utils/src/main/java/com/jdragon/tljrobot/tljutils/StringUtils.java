package com.jdragon.tljrobot.tljutils;

/**
 * @Author JDragon
 * @Date 2021.07.07 下午 7:31
 * @Email 1061917196@qq.com
 * @Des:
 */
public class StringUtils {
    /**
     * 判断是否为空字符串，包括空格也算.
     *
     * @param cs 待判断字符串
     * @return 是否的布尔结果
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }

        // 遍历每个空格是否有非空格元素
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 指定字符串是否被包装
     *
     * @param str        字符串
     * @param prefixChar 前缀
     * @param suffixChar 后缀
     * @return 是否被包装
     */
    public static boolean isWrap(CharSequence str, char prefixChar, char suffixChar) {
        if (null == str) {
            return false;
        }

        return str.charAt(0) == prefixChar && str.charAt(str.length() - 1) == suffixChar;
    }
}
