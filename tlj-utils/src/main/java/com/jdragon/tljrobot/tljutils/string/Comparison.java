package com.jdragon.tljrobot.tljutils.string;

import java.util.*;

/**
 * Create by Jdragon on 2020.02.03
 */
public class Comparison {

    class DataType {
        int distance;
        int prevX;
        int prevY;
        public DataType(int distance, int prevX, int prevY) {
            this.distance = distance;
            this.prevX = prevX;
            this.prevY = prevY;
        }
        public int getDistance() {
            return distance;
        }
        public int getPrevX() {
            return prevX;
        }
        public int getPrevY() {
            return prevY;
        }
//		public void setDistance(int distance) {
//			this.distance = distance;
//		}
//		public void setPrevXY(int prevX, int prevY) {
//			this.prevX = prevX;
//			this.prevY = prevY;
//		}
    }

    public ArrayList<int[]> getMatch(String origin, String typed) {
        int lenOrigin = origin.length();
        int lenTyped = typed.length();
        DataType[][] data = new DataType[lenOrigin + 1][lenTyped + 1];
        data[0][0] = new DataType(0, -1, -1); // previous (-1, -1) means start point
        for (int j = 1; j < lenTyped + 1; j++) {
            data[0][j] = new DataType(j, 0, j - 1); // origin empty, type is number of differences
        }
        for (int i = 1; i < lenOrigin + 1; i++) {
            data[i][0] = new DataType(i, i - 1, 0); // type is empty, origin is number of differences
            for (int j = 1; j < lenTyped + 1; j++) {
                int m = i - 1;
                int n = j - 1;
                if (origin.charAt(m) == typed.charAt(n)) {
                    data[i][j] = new DataType(data[i - 1][j - 1].getDistance(), i - 1, j - 1);
                } else {
                    int distanceLess = data[i - 1][j].getDistance(); // if typed less
                    int distanceWrong = data[i - 1][j - 1].getDistance(); // if typed wrong
                    int distanceMore = data[i][j - 1].getDistance(); // if typed more
                    if (distanceWrong <= distanceLess && distanceWrong <= distanceMore) {
                        data[i][j] = new DataType(distanceWrong + 1, i - 1, j - 1);
                    } else if (distanceLess <= distanceWrong && distanceLess <= distanceMore) {
                        data[i][j] = new DataType(distanceLess + 1, i - 1, j);
                    } else {
                        data[i][j] = new DataType(distanceMore + 1, i, j - 1);
                    }
                }
            }
        }
//		int destX = lenOrigin - 1;
//		int destY = lenTyped - 1;
        int curX = lenOrigin;
        int curY = lenTyped;
        int[] resOrigin = new int[lenOrigin];
        int[] resTyped = new int[lenTyped];
        ArrayList<int[]> a = new ArrayList<int[]>();
        while(curX != 0 || curY != 0) {
            int prevX = data[curX][curY].getPrevX();
            int prevY = data[curX][curY].getPrevY();
            if (data[prevX][prevY].getDistance() == data[curX][curY].getDistance() - 1) {
                if (prevX < curX && prevY < curY) {  //Wrong word
                    resOrigin[curX - 1] = 3;
                    resTyped[curY - 1] = 3;
                } else if (prevX < curX) {	//more word
                    resOrigin[curX - 1] = 1;
                } else {					//less word
                    resTyped[curY - 1] = 1;

                }
            }
            curX = prevX;
            curY = prevY;
        }
        a.add(resOrigin);
        a.add(resTyped);
//        System.out.println(origin);
//        System.out.println(Arrays.toString(resOrigin));
//        System.out.println(typed);
//        System.out.println(Arrays.toString(resTyped));
        return a;
    }
    public static List<HashMap<String,Integer>> getComparisonResult(String origin,String typed) {
        // TODO Auto-generated method stub
        Comparison am = new Comparison();


        ArrayList<int[]> strMatch = am.getMatch(origin, typed);
        char[] originChars = origin.toCharArray();
        char[] typedChars = typed.toCharArray();
        int [] orgins = strMatch.get(0);
        int [] typeds = strMatch.get(1);
        List<HashMap<String,Integer>> strList = new ArrayList<>();
        int x = 0;
        int y = 0;
        while (x!=originChars.length||y!=typedChars.length){
            HashMap<String,Integer> hashMap = new HashMap<>();
            if(x!=originChars.length&&orgins[x]==1){
                hashMap.put(String.valueOf(originChars[x]),1);//少字
                strList.add(hashMap);
                x++;
            }else if(y!=typedChars.length&&typeds[y]==1){
                hashMap.put(String.valueOf(typedChars[y]),2);//多字
                strList.add(hashMap);
                y++;
            }else if(x!=originChars.length&&orgins[x]==3){
                hashMap.put(String.valueOf(originChars[x]),4);//错原字
                strList.add(hashMap);

                hashMap = new HashMap<>();
                hashMap.put(String.valueOf(typedChars[y]),3);//错字
                strList.add(hashMap);

                x++;y++;
            }else{
                hashMap.put(String.valueOf(originChars[x]),0);//正确字
                strList.add(hashMap);
                x++;y++;
            }
        }

        return strList;
    }
    public static List<HashMap<String,Integer>> getComparisonListenResult(String origin,String typed,HashMap<String,String> symbolCode) {
        // TODO Auto-generated method stub
        Comparison am = new Comparison();

        StringBuilder newOrigin = new StringBuilder();
        StringBuilder newTyped = new StringBuilder();

        List<Integer> typedSymbolSign = new ArrayList<>();
        List<Integer> originSymbolSign = new ArrayList<>();
        for(int i = 0;i<origin.length();i++){
            if(!symbolCode.containsKey(String.valueOf(origin.charAt(i)))){
                newOrigin.append(origin.charAt(i));
            }else originSymbolSign.add(i);
        }
        for(int i = 0;i<typed.length();i++){
            if(!symbolCode.containsKey(String.valueOf(typed.charAt(i)))){
                newTyped.append(typed.charAt(i));
                typedSymbolSign.add(i);
            }
        }

        ArrayList<int[]> strMatch = am.getMatch(newOrigin.toString(), newTyped.toString());
        char[] originChars = newOrigin.toString().toCharArray();
        char[] typedChars = newTyped.toString().toCharArray();
        int [] orgins = strMatch.get(0);
        int [] typeds = strMatch.get(1);

        List<HashMap<String,Integer>> strList = new ArrayList<>();
        int x = 0;
        int y = 0;
        int symbolNum = 0;
        while (x!=originChars.length||y!=typedChars.length){
            HashMap<String,Integer> hashMap = new HashMap<>();
            if (originSymbolSign.contains(x+symbolNum)){
                hashMap.put(String.valueOf(origin.charAt(x+symbolNum)),5);//忽略的符号
                strList.add(hashMap);
                symbolNum++;
                continue;
            }
            if(x!=originChars.length&&orgins[x]==1){
                hashMap.put(String.valueOf(originChars[x]),1);//少字
                strList.add(hashMap);
                x++;
            }else if(y!=typedChars.length&&typeds[y]==1){
                hashMap.put(String.valueOf(typedChars[y]),2);//多字
                strList.add(hashMap);
                y++;
            }else if(x!=originChars.length&&orgins[x]==3){
                hashMap.put(String.valueOf(originChars[x]),4);//错原字
                strList.add(hashMap);

                hashMap = new HashMap<>();
                hashMap.put(String.valueOf(typedChars[y]),3);//错字
                strList.add(hashMap);

                x++;y++;
            }else{
                hashMap.put(String.valueOf(originChars[x]),0);//正确字
                strList.add(hashMap);
                x++;y++;
            }
        }

        return strList;
    }
    public static void main(String []args){
//        String origin = "七月二日正是浙江与上海的社员乘车赴会的日子。这上海这样大车站里，多了几十个改进社社员，原也不一定能显出什么异样；但我却觉得确乎是不同了，“一时之盛”的光景，在车站的一角上，是显然可见的。这是在茶点室的左边；那里丛着一群人，正在向两位特派的招待员接洽。壁上贴着一张黄色的磅纸，写着龙蛇飞舞的字：“二等四元，三等二元。”两位招待员开始执行职务了；这时已是六点四十分，离开车还有二十分钟了。招待员所应做的第一大事，自然是买车票。买车票是大家都会的，买半票却非由他们二位来“优待”一下不可。“优待”可真不是容易的事！他们实行“优待”的时候，要向每个人取名片，票价，——还得找钱。他们往还于茶点室与售票处之间，少说些，足有二十次！他们手里是拿着一叠名片和钞票洋钱；眼睛总是张望着前面，仿佛遗失了什么，急急寻觅一样；面部筋肉平板地紧张着；手和足的运动都像不是他们自己的。好容易费了二虎之力，居然买了几张票，凭着名片分发了。每次分发时，各位候补人都一拥而上。";
//        String typed = "七月";
        String origin = "为什么呢。为什么这样呢。我知道，三。。三";
        String typed = "为什么呢。为什么这样呢。我知道，三。。三";
//        for(HashMap<String,Integer> hashMap:getComparisonResult(origin,typed)){
//            for(Map.Entry<String,Integer> entry:hashMap.entrySet()){
//                System.out.println(entry.getKey()+":"+entry.getValue());
//            }
//        }
        HashMap<String,String> symbolCode = new HashMap<>();
        List<Integer> typedSymbolSign = new ArrayList<>();
        List<Integer> originSymbolSign = new ArrayList<>();
        symbolCode.put("。",".");
        symbolCode.put("，",".");
        StringBuilder newOrigin = new StringBuilder();
        StringBuilder newTyped = new StringBuilder();

        for(int i = 0;i<origin.length();i++){
            if(!symbolCode.containsKey(String.valueOf(origin.charAt(i)))){
                newOrigin.append(origin.charAt(i));
            }else originSymbolSign.add(i);
        }
        for(int i = 0;i<typed.length();i++){
            if(!symbolCode.containsKey(String.valueOf(typed.charAt(i)))){
                newTyped.append(typed.charAt(i));
            }
        }
        Comparison am = new Comparison();
        System.out.println(newOrigin);
        System.out.println(newTyped);
        ArrayList<int[]> strMatch = am.getMatch(newOrigin.toString(), newTyped.toString());
        int [] orgins = strMatch.get(0);
        int [] typeds = strMatch.get(1);
        for(Integer orgin:orgins) {
            System.out.print(orgin);
        }
        System.out.println();
        for(Integer type:typeds) {
            System.out.print(type);
        }
        char[] originChars = newOrigin.toString().toCharArray();
        char[] typedChars = newTyped.toString().toCharArray();
        List<HashMap<String,Integer>> strList = new ArrayList<>();
        int x = 0;
        int y = 0;
        int symbolNum = 0;
        while (x!=originChars.length||y!=typedChars.length) {
            HashMap<String, Integer> hashMap = new HashMap<>();
            if (originSymbolSign.contains(x + symbolNum)) {
                hashMap.put(String.valueOf(origin.charAt(x+symbolNum)), 5);//忽略的符号
                strList.add(hashMap);
                symbolNum++;
            }
            if (x != originChars.length && orgins[x] == 1) {
                hashMap.put(String.valueOf(originChars[x]), 1);//少字
                strList.add(hashMap);
                x++;
            } else if (y != typedChars.length && typeds[y] == 1) {
                hashMap.put(String.valueOf(typedChars[y]), 2);//多字
                strList.add(hashMap);
                y++;
            } else if (x != originChars.length && orgins[x] == 3) {
                hashMap.put(String.valueOf(originChars[x]), 4);//错原字
                strList.add(hashMap);

                hashMap = new HashMap<>();
                hashMap.put(String.valueOf(typedChars[y]), 3);//错字
                strList.add(hashMap);

                x++;
                y++;
            } else {
                hashMap.put(String.valueOf(originChars[x]), 0);//正确字
                strList.add(hashMap);
                x++;
                y++;
            }
        }
        System.out.println(strList);
    }
}
