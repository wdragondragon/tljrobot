package com.jdragon.tljrobot.tljutils;

import java.io.*;

public class CodeLength {
    static int n = 0;
    static int strNum = 0;
    static int strLength = 0;
    static StringBuilder strAll = new StringBuilder();
    public static void main(String[] args) throws IOException {
//        String path  = "C:\\Users\\10619\\IdeaProjects\\otherPro\\haofangerp";
        String path = "D:\\HBuilderProjects\\HaoFangErp-UI\\src";
        folderMethod2(path);
        strLength = strAll.length();
        System.out.println("一共"+n+"个类");
        System.out.println("一共"+strNum+"行代码");
        System.out.println("一共"+strLength+"个字符");
    }
    public static void folderMethod2(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        folderMethod2(file2.getAbsolutePath());
                    } else {
                        if(file2.getAbsolutePath().contains(".vue")) {
                            int strNumTemp = 0;
                            int strLengthTemp = 0;
                            String str;
                            FileInputStream fis = new FileInputStream(file2.getAbsolutePath());
                            InputStreamReader read = new InputStreamReader(fis, "UTF-8");
                            BufferedReader bufferRead = new BufferedReader(read);
                            while ((str = bufferRead.readLine()) != null) {
                                strLengthTemp += str.length();
                                strAll.append(str);
                                strNumTemp++;
                            }
                            strNum += strNumTemp;
                            n++;
                            System.out.println("文件:" + file2.getAbsolutePath()+" 行："+strNumTemp+" 字："+ strLengthTemp);
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
}
