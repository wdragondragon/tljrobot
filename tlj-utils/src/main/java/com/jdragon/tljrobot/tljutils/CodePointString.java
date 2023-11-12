package com.jdragon.tljrobot.tljutils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: JDragon
 * @Data:2022/11/11 23:09
 * @Description:
 */
public class CodePointString {

    private final String string;

    private final String[] chars;

    public final int length;

    public CodePointString(String string) {
        this.string = string;
        this.chars = toArticleChars(string);
        this.length = chars.length;
    }


    public CodePointString(String string, boolean origin) {
        this.string = string;
        if (origin) {
            char[] chars = string.toCharArray();
            this.chars = new String[chars.length];
            for (int i = 0; i < chars.length; i++) {
                this.chars[i] = Character.toString(chars[i]);
            }
        } else {
            this.chars = toArticleChars(string);
        }
        this.length = chars.length;
    }

    public static String[] toArticleChars(String articleStr) {
        List<String> articleCodePoint = new LinkedList<>();
        for (int i = 0; i < articleStr.length(); i++) {
            if (i + 1 == articleStr.length() || articleStr.codePointCount(i, i + 2) > 1) {
                articleCodePoint.add(articleStr.substring(i, i + 1));
            } else {
                articleCodePoint.add(articleStr.substring(i, i + 2));
                i = i + 1;
            }
        }
        return articleCodePoint.toArray(new String[0]);
    }

    public String substring(int start, int end) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = start; i < end; i++) {
            stringBuilder.append(chars[i]);
        }
        return stringBuilder.toString();
    }

    public String substring(int start) {
        return substring(start, chars.length - 1);
    }

    public String trim() {
        return string.trim();
    }

    public int length() {
        return length;
    }

    public String val() {
        return string;
    }

    @Override
    public int hashCode() {
        return string.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CodePointString) {
            return equals((CodePointString) obj);
        } else if (obj instanceof String) {
            return string.equals(obj);
        } else {
            return super.equals(obj);
        }
    }

    public boolean equals(CodePointString obj) {
        return string.equals(obj.string);
    }

    @Override
    public String toString() {
        return string;
    }

    public String[] toCharArray() {
        return chars;
    }

    public String charAt(int index) {
        return chars[index];
    }

    public String[] split(String regex) {
        return string.split(regex);
    }
}
