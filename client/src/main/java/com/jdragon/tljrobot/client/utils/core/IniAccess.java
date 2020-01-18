package com.jdragon.tljrobot.client.utils.core;
import com.jdragon.tljrobot.client.config.LocalConfig;
import lombok.SneakyThrows;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Create by Jdragon on 2020.01.16
 */
public class IniAccess {
    @SneakyThrows
    public static void readIni(String filePath) {
        File file = new File(filePath);
        if(!file.exists())return;
        Ini ini = new Ini();
        ini.load(file);
        for(Entry<String,Section> entry : ini.entrySet()){
            Section sectionIndex = entry.getValue();
            Class clazz = LocalConfig.class;
            for(String configEntry:sectionIndex.keySet()) {
                Field field = clazz.getDeclaredField(configEntry);
                Class typeClass = field.getType();
                Constructor constructor;
                Object obj;
                if(typeClass.getSimpleName().equals("Color")) {
                    constructor = typeClass.getConstructor(int.class,int.class,int.class);
                    Integer[] rgb = getColorRgb(sectionIndex.get(configEntry));
                    obj = constructor.newInstance(rgb[0],rgb[1],rgb[2]);
                }else{
                    constructor = typeClass.getConstructor(String.class);
                    obj = constructor.newInstance(sectionIndex.get(configEntry));
                }
                field.setAccessible(true);
                field.set(null,obj);
            }
        }
    }
    @Test
    public void test(){
        readIni("test.ini");
        writeIni("test.ini");
    }
    public static void writeIni(String filePath)  {
        try {
            File file = new File(filePath);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            Ini ini = new Ini();
//            ini.load();
            String section = LocalConfig.configSection;
            Class clazz = LocalConfig.class;
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                ini.add(section, field.getName(), field.get(clazz));
            }
            ini.store(new FileWriter(filePath,false));
        }catch (Exception e){e.printStackTrace();}
    }
    private static Integer[] getColorRgb(String color){
        Integer[] rgb = new Integer[3];
        String[] regexRGB = new String[]{"r=(.*?),","g=(.*?),","b=(.*?)]"};
        for(int i = 0;i<rgb.length;i++){
            Pattern p = Pattern.compile(regexRGB[i]);
            Matcher m = p.matcher(color);
            if(m.find()){
                rgb[i] = Integer.parseInt(m.group(1));
            }
        }
        return rgb;
    }
}