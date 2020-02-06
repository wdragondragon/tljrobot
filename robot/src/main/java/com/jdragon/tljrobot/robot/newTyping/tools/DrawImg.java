package com.jdragon.tljrobot.robot.newTyping.tools;

import com.jdragon.tljrobot.robot.newTyping.entry.RobotHistory;
import com.jdragon.tljrobot.robot.newTyping.entry.TljHistory;
import com.jdragon.tljrobot.robot.typing.Tools.Createimg;

import java.util.*;

/**
 * Create by Jdragon on 2020.01.23
 */
public class DrawImg {
    public static String unionMatchByHistories(String title, String[] head, List<RobotHistory> robotHistories){
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
            String name = "未知";
            String groupName = "未知";

            long groupId = robotHistory.getGroupId();
            HashMap<Long,String> groupMemberCardList = GroupCache.groupCardCache.get(groupId);
            if(groupMemberCardList!=null&&GroupCache.typeGroupMap.containsKey(groupId)) {
                name = groupMemberCardList.get(robotHistory.getQq());
                groupName = GroupCache.typeGroupMap.get(groupId);
            }
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
            contentArray.add(Arrays.asList(name,groupName,SpeedStr,keySpeedStr,keyLengthStr,deleteNumStr,deleteTextStr,repeatNumStr,mistakeStr,keyAccuracyStr,typeNumStr));
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
    public static String tljMatchByHistories(String title,String[] head,List<TljHistory> tljHistories){
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
        for(TljHistory tljHistory:tljHistories){
            String name = tljHistory.getUserName();
            String speed = String.valueOf(tljHistory.getSpeed());
            String keySpeed = String.valueOf(tljHistory.getKeySpeed());
            String keyLength = String.valueOf(tljHistory.getKeyLength());
            String deleteNum = String.valueOf(tljHistory.getDeleteNum());
            String deleteText = String.valueOf(tljHistory.getDeleteText());
            String mistake = String.valueOf(tljHistory.getMistake());
            String repeatNum = String.valueOf(tljHistory.getRepeatNum());
            String keyAccuracy = String.valueOf(tljHistory.getKeyAccuracy());
            String keyMethod = String.valueOf(tljHistory.getKeyMethod());
            String wordRate = String.valueOf(tljHistory.getWordRate());
            String equipment;
            if(tljHistory.isMobile())equipment = "手机端";
            else equipment = "PC端";
            keySpeedList.add(tljHistory.getKeySpeed());
            keyLengthList.add(tljHistory.getKeyLength());
            contentArray.add(Arrays.asList(name,speed,keySpeed,keyLength,deleteNum,deleteText,mistake,repeatNum,keyAccuracy,keyMethod,wordRate,equipment));
        }
        allValue.add(contentArray);
        Collections.sort(keySpeedList,Collections.reverseOrder());
        Collections.sort(keyLengthList);
        HashMap<Integer, List<Double>> rankMap = new HashMap<>();
        rankMap.put(3,keySpeedList);
        rankMap.put(4,keyLengthList);
        try {
            path = Createimg.graphicsGeneration(allValue,titles,heads,null,heads.get(0).length,rankMap);
        } catch (Exception e) {
            path = "生成图片出错";
            e.printStackTrace();
        }
        return path;
    }
//    public static String unionMatchFirstPlayByHistories(String title, String[] head, List<RobotHistory> robotHistories){
//
//    }
}
