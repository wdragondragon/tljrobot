package com.jdragon.tljrobot.robot.newTyping.tools;

import com.jdragon.tljrobot.robot.newTyping.entry.RobotHistory;
import com.jdragon.tljrobot.robot.typing.Tools.Createimg;

import java.util.*;

/**
 * Create by Jdragon on 2020.01.23
 */
public class DrawImg {
    public static String tljMatchByHistories(String title,String[] head,List<RobotHistory> robotHistories){
        //处理返回路径或处理结果
        String path;
        //表格基本信息项
        List<List<List<String>>> allValue = new ArrayList<List<List<String>>>();
        List<List<String>> contentArray = new ArrayList<List<String>>();
        List<String[]> heads = new ArrayList<String[]>();
        List<String> titles = new ArrayList<String>();
        //两链表作排名使用
        List<Double> keySpeedList = new ArrayList<>();
        List<Double> keyLengthList = new ArrayList<>();

        titles.add(title);
        heads.add(head);
        for(RobotHistory robotHistory:robotHistories) {
//            String name = initGroupList.QQlist.get(robotHistory.getQq());
            String name = "谭宇";
//            String groupName = initGroupList.QQGroupMap.get(robotHistory.getGroupId());
            String groupName = "练习群";
            Double keySpeed = robotHistory.getKeySpeed();
           Double keyLength = robotHistory.getKeyLength();
            String SpeedStr = String.valueOf(robotHistory.getSpeed());

            String keySpeedStr = String.valueOf(keySpeed);
            if(keySpeedStr.equals("-1.0"))
                keySpeedStr = "无";
            else if(keyLength<4)
                keySpeedList.add(keySpeed);

            String keyLengthStr = String.valueOf(keyLength);
            if(keyLengthStr.equals("-1.0"))
                keyLengthStr = "无";
            else if(keySpeed>4)
                keyLengthList.add(keyLength);

            String typeNumStr = String.valueOf(robotHistory.getTypeNum());

            String deleteNumStr = String.valueOf(robotHistory.getDeleteNum());
            if (deleteNumStr.equals("-1"))
                deleteNumStr = "无";

            String deleteTextStr = String.valueOf(robotHistory.getDeleteText());
            if(deleteTextStr.equals("-1"))
                deleteTextStr = "无";

            String repeatNumStr = String.valueOf(robotHistory.getRepeatNum());
            if(repeatNumStr.equals("-1"))
                repeatNumStr = "无";

            String mistakeStr = String.valueOf(robotHistory.getMistake());
            if(mistakeStr.equals("-1"))
                mistakeStr = "无";

            String keyAccuracyStr = String.valueOf(robotHistory.getKeyAccuracy());
            if(keyAccuracyStr.equals("-1.0"))
                keyAccuracyStr = "无";
            else
                keyAccuracyStr += "%";
            contentArray.add(Arrays.asList(new String[]{name,groupName,SpeedStr,keySpeedStr,keyLengthStr,deleteNumStr,deleteTextStr,repeatNumStr,mistakeStr,keyAccuracyStr,typeNumStr}));
        }
        allValue.add(contentArray);
        Collections.sort(keySpeedList,Collections.reverseOrder());
        Collections.sort(keyLengthList);
        HashMap<Integer, List<Double>> rankMap = new HashMap<>();
        rankMap.put(4,keySpeedList);
        rankMap.put(5,keyLengthList);
        try {
            path = Createimg.graphicsGeneration(allValue,titles,heads,null,heads.get(0).length,rankMap);
        } catch (Exception e) {
            path = "生成图片出错";
            e.printStackTrace();
        }
        return path;
    }
}
