package com.jdragon.tljrobot.robot.typing.ConDatabase;

import com.jdragon.tljrobot.robot.typing.Tools.Createimg;
import com.jdragon.tljrobot.robot.typing.Tools.RegexText;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class OutConn {
    public static String ShowRobotSaiwen(String type ,int aid ,int model){
        String message = "";
        try {
            Connection con = Conn.getConnection();
            PreparedStatement ptmt;
            ResultSet rs;
            if (model==2){
                String sql = "select * from robotsaiwen where aid=? and type=?";
                ptmt = Conn.getPtmt(con, sql);
                ptmt.setInt(1, aid);
                ptmt.setString(2, type);
                rs = ptmt.executeQuery();
                if (rs.next()) {
                    String title = rs.getString("title");
                    String saiwen = rs.getString("saiwen");
                    String authorQQ = rs.getString("authorQQ");
                    message = title + "投稿自：" + authorQQ + "\n" + saiwen + "\n-----第1段-共" + saiwen.length() + "字";
                } else {
                    message = "没有这篇文章";
                }
            }else {
                String sql = "select * from robotsaiwen where type=? order by aid DESC";
                ptmt = Conn.getPtmt(con,sql);
                ptmt.setString(1, type);
                rs = ptmt.executeQuery();
                boolean sign = false;
                while(rs.next()){
                    aid = rs.getInt("aid");
                    String title = rs.getString("title");
                    int num = rs.getString("saiwen").length();
                    message += type + " "+ aid +" "+ title + " 字数:" +num +"\n";
                    sign = true;
                }
                if (!sign) {
                    message = "无该类文章";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            message = "获取文章出错";
        }
        return message;
    }
    public static String ShowGroupIdMath(Long id, String groupname, Date date, String idname) {
        boolean have = false;
        String message = date+"\n用户名："+idname+" 群："+groupname;
//        String sql = "select id from robotclient where name=?";
        Long groupid;
        try{
            Connection con = Conn.getConnection();
            String sql = "select groupid from groupmap where groupname=?";
            PreparedStatement ptmt = Conn.getPtmt(con,sql);
            ptmt.setString(1,groupname);
            ResultSet rs = ptmt.executeQuery();
            if(rs.next()) {
                groupid=rs.getLong("groupid");
            } else{ con.close();return "无该群映射列表，发送指令（群映射列表）来查看可以查询的群名";}
            sql = "select * from robothistory where id=? and groupid=? and date=?";
            ptmt = Conn.getPtmt(con,sql);
            ptmt.setLong(1,id);
            ptmt.setLong(2,groupid);
            ptmt.setDate(3,date);
            rs = ptmt.executeQuery();
            List<List<List<String>>> allValue = new ArrayList<List<List<String>>>();
            List<List<String>> contentArray = new ArrayList<List<String>>();
            List<String[]> headTitles = new ArrayList<String[]>();
            List<String> titles = new ArrayList<String>();
            titles.add(idname+"的"+date+"成绩");
            headTitles.add(new String[]{"序号","名字","成绩","击键","码长"});
            allValue.add(contentArray);
            List<Double> keylist = new ArrayList<>();
            List<Double> keylenthlist = new ArrayList<>();
            while(rs.next()){
                keylist.add(RegexText.FourOutFiveIn(rs.getDouble("keyspeed")));
                keylenthlist.add(RegexText.FourOutFiveIn(rs.getDouble("keylength")));
                contentArray.add(Arrays.asList(idname
                        ,String.valueOf(RegexText.FourOutFiveIn(rs.getDouble("speed")))
                        ,String.valueOf(RegexText.FourOutFiveIn(rs.getDouble("keyspeed")))
                        ,String.valueOf(RegexText.FourOutFiveIn(rs.getDouble("keylength")))));
                have = true;
            }
            if(have==false)
                message = "无收录成绩";
            else {
                Collections.sort(keylist,Collections.reverseOrder());
                Collections.sort(keylenthlist);
                HashMap<Integer, List<Double>> rankmap = new HashMap<>();
                rankmap.put(3,keylist);
                rankmap.put(4,keylenthlist);
                message = Createimg.graphicsGeneration(allValue,titles,headTitles,null,headTitles.get(0).length,rankmap);
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  message;
    }
    public static long getTodayMaxByGroupId(long groupid,double sudu,int duan){
        String sql;
        if (duan != 999) {
            sql = "select sudu,id from allgroupsaiwenchengji where saiwendate=? order by sudu desc limit 1";
        } else {
            sql = "select speed,id from groupsaiwenmax where date=? and groupid=? order by speed desc limit 1";
        }
        try{
            Connection con = Conn.getConnection();
            PreparedStatement ptmt = Conn.getPtmt(con,sql);
            ptmt.setDate(1,Conn.getdate());
            if(duan==999) {
                ptmt.setLong(2,groupid);
            }
            ResultSet rs = ptmt.executeQuery();
            if(rs.next()){
                double speed;
                if(duan==999) {
                    speed = rs.getDouble(("speed"));
                } else {
                    speed = rs.getDouble(("sudu"));
                }
                if(speed<sudu){
                    long ret= rs.getLong("id");
                    con.close();
                    return ret;
                }
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0L;
    }
    public static String lookmeAllGroupSaiwen(long id){
        String sql = "select * from allgroupsaiwen where author="+id;
        String message = "";
        try{
            Connection con = Conn.getConnection();
            ResultSet rs = Conn.getStmtSet(con,sql);
            while(rs.next()){
                String date = String.valueOf(rs.getDate("saiwendate"));
                String title = rs.getString("title");
                String saiwen = rs.getString("saiwen");
                message += "日期："+date+" 标题："+title+" 字数："+saiwen.length()+"\n";
            }
            if(message.equals("")) {
                message = "你未投稿任何一篇赛文";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return message;
    }
    public static HashMap<Long,Boolean> getGroupList(){
        HashMap<Long,Boolean> grouplist = new HashMap();
        String sql  = "select * from groupmap";
        try{
            ResultSet rs = Conn.getStmtSet(Conn.getConnection(),sql);
            while(rs.next()){
                long groupid = rs.getLong("groupid");
                grouplist.put(groupid,false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return grouplist;
    }
    public static HashMap<Long,String> getGroupMap(){
        HashMap<Long,String> groupmap = new HashMap<>();
        String sql = "select * from groupmap";
        Connection connection = Conn.getConnection();
        try{
            ResultSet rs = Conn.getStmtSet(connection,sql);
            while(rs.next()){
                groupmap.put(rs.getLong("groupid"),rs.getString("groupname"));
            }
            connection.close();
        }catch (Exception e){
            connection.close();
            e.printStackTrace();
        }finally {
            return groupmap;
        }
    }
}
