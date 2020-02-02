package com.jdragon.tljrobot.client.utils.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static java.io.File.separator;

/**
 * Create by Jdragon on 2020.01.31
 */
public class Code {
    public HashMap<String,Integer> allTable = new HashMap<>();
    public HashMap<String,Integer> otherTable = new HashMap<>();
    public HashMap<String,Integer> firstTable = new HashMap<>();
    private static Code code = null;
    public static Code getInstance(String ciZuFileName){
        if(code==null)
            code = new Code(ciZuFileName);
        return code;
    }
    private Code(String ciZuFileName){
        try{
            File more = new File("编码文件"+separator+"输入法编码"+separator+ciZuFileName+".txt");
            FileInputStream fis = new FileInputStream(more);
            InputStreamReader read = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader bufferRead = new BufferedReader(read);
            String str;
            String regex = "234567890";
            while((str=bufferRead.readLine())!=null){
                String[] splited = str.split("\\s+");
                if(splited.length!=2)continue;
                String ch = splited[0];
                String bm = splited[1];
                String temp;
                int length = splited[1].length();
                temp = bm.substring(bm.length()-1);
                if(temp.equals("_")||regex.contains(temp))length -= 1;
                if(allTable.containsKey(splited[0])){
                    if(allTable.get(splited[0])>length){
                        allTable.put(ch, length);
                    }
                    else if(allTable.get(splited[0])==length&&length==4){
                        if(!regex.contains(temp)){
                            allTable.put(ch, length);
                        }
                    }
                }
                else{
                    allTable.put(ch, length);
                }
                if(regex.contains(temp)) {
                    if (otherTable.containsKey(ch)) {
                        if (otherTable.get(splited[0]) > length) {
                            otherTable.put(ch, length);
                        }
                    } else {
                        otherTable.put(ch, length);
                    }
                }else{
                    if (firstTable.containsKey(ch)) {
                        if (firstTable.get(splited[0]) > length) {
                            firstTable.put(ch, length);
                        }
                    } else {
                        firstTable.put(ch, length);
                    }
                }
            }
            bufferRead.close();
            read.close();
            fis.close();
        }catch(Exception e){
            System.out.println("打开失败2");
            e.printStackTrace();
        }
    }
}
