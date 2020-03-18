package com.jdragon.tljrobot.robot.typing.Tools;

import java.util.*;

public class SortMap {
    public static String SendsortKey(Map<Long,Integer> map,Map<Long,SortMap>namelist){
        StringBuilder message= new StringBuilder();
        Set set = map.keySet();
        Long[] arr = (Long[]) set.toArray();
        Arrays.sort(arr);
        for (Long key : arr) {
            System.out.println(key + ": " + map.get(key));
            message.append("用户名：")
                    .append(namelist.get(key))
                    .append(" 用户Q号：")
                    .append(key)
                    .append(" 得分：")
                    .append(map.get(key))
                    .append("\n");
        }
        return message.toString();
    }
    public static String SendsortValue(Map<Long,Integer> map,Map<Long,String> namelist){
        StringBuilder message= new StringBuilder();
        //value-sort
        List<Map.Entry<Long, Integer>> list = new ArrayList<>(map.entrySet());
        //collections.sort()
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        //for-each
        for (Map.Entry<Long, Integer> mapping : list) {
            System.out.println(mapping.getKey() + ": " + mapping.getValue());
            message.append("用户名：")
                    .append(namelist.get(mapping.getKey()))
                    .append(" 用户Q号：")
                    .append(mapping.getKey())
                    .append(" 得分：")
                    .append(mapping.getValue())
                    .append("\n");
        }//OutConn.insteadName(mapping.getKey())
        return message.toString();
    }
    public static String SendsortValueFollow(Map<Long,Integer> math,Map<Long, Double> map,Map<Long,String>namelist){
        StringBuilder message= new StringBuilder();
        //value-sort
        List<Map.Entry<Long, Double>> list = new ArrayList<>(map.entrySet());
        //collections.sort()
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        //for-each
        int i = 0;
        for (Map.Entry<Long, Double> mapping : list) {
            if(i<3) {
                math.put(mapping.getKey(),(math.get(mapping.getKey())+3-i));
            }
            i++;// OutConn.insteadName(mapping.getKey())
            message.append("用户名：")
                    .append(namelist.get(mapping.getKey()))
                    .append(" 用户Q号：")
                    .append(mapping.getKey())
                    .append(" 得分：")
                    .append(math.get(mapping.getKey()))
                    .append(" 该段速度：")
                    .append(mapping.getValue())
                    .append("\n");
        }
        return message.toString();
    }
    public static String SendsortValueTeamOneSpeed(Map<Long,Double> map,Map<Long,String> namelist){
        StringBuilder message= new StringBuilder();
        //value-sort
        List<Map.Entry<Long, Double>> list = new ArrayList<>(map.entrySet());
        //collections.sort()
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        //for-each
        for (Map.Entry<Long, Double> mapping : list) {
            System.out.println(mapping.getKey() + ": " + mapping.getValue());
            message.append("用户名：").append(namelist.get(mapping.getKey())).append(//OutConn.insteadName(mapping.getKey())
                    " 用户Q号：").append(mapping.getKey()).append(" 该段速度：").append(mapping.getValue()).append("\n");
        }
        return message.toString();
    }
    public static String SendsortValueTeamSpeed(Map<Integer,Double> map,Map<Integer,Integer> math){
        StringBuilder message= new StringBuilder();
        //value-sort
        List<Map.Entry<Integer,Double>> list = new ArrayList<>(map.entrySet());
        //collections.sort()
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        //for-each
        int i=0;
        for (Map.Entry<Integer,Double> mapping : list) {
            System.out.println(mapping.getKey() + ": " + mapping.getValue());
            if(i==0){
                math.put(mapping.getKey(),(math.get(mapping.getKey())+1));
            }
            message.append("团队号：")
                    .append(mapping.getKey())
                    .append(" 队员平均速度：")
                    .append(mapping.getValue())
                    .append("队伍得分：")
                    .append(math.get(mapping.getKey()))
                    .append("\n");
            i++;
        }
        return message.toString();
    }
    public static String SendsortValueTeamMath(Map<Integer,Integer> map){
        StringBuilder message= new StringBuilder();
        //value-sort
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        //collections.sort()
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        //for-each
        for (Map.Entry<Integer, Integer> mapping : list) {
            System.out.println(mapping.getKey() + ": " + mapping.getValue());
            message.append("队伍号：").append(mapping.getKey()).append(" 得分：").append(mapping.getValue()).append("\n");
        }
        return message.toString();
    }
}
