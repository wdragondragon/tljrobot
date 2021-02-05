package com.jdragon.tljrobot.client.utils.common;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by Jdragon on 2020.01.13
 */
public class DoCheck {
    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    private static final String ENCRYPT_KEY = "1072970551";
    public static String buildCheckStr(String str, String model){
        char[] d = str.toCharArray();
        char[] c = model.toCharArray();
        for (int i = 0; i < d.length; i++) {
            d[i] = (char) (d[i] ^ c[i%5]);
        }
        return new String(d);
    }

    public static boolean check(String str, String model){
        String []regex = {"速度(.*?) ","击键(.*?) ","码长(.*?) "};
        double a=0.0;
        for (String s : regex) {
            Pattern pattern = Pattern.compile(s);//匹配模式
            Matcher m = pattern.matcher(str);//判断是否符合匹配
            if (m.find()) {
                String temp = m.group(1);
                if (temp.contains("/")) {
                    temp = temp.split("/")[0];
                }
                a += Double.parseDouble(String.format("%.2f", Double.parseDouble((temp))));

            }
        }
        String Check = str.substring(str.indexOf("校验")+2);
        String temp = String.format("%.2f", a);
        String check = buildCheckStr(temp,model);
        return check.equals(Check);
    }

    public static String hmacSHA1Encrypt(String encryptText) throws Exception {
        byte[] data = ENCRYPT_KEY.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] textBytes = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        String text = byte2hex(mac.doFinal(textBytes));
        return text.substring(text.length() - 8);
    }

    //二行制转字符串
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
}
