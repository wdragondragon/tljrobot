package com.jdragon.tljrobot.robot.typing.ConDatabase;

import com.jdragon.tljrobot.robot.typing.Tools.initGroupList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InConn {
    public static void addMaxComMath(Long id,Long groupid,double[] Com){
        try{
            String sql = "select * from groupsaiwenmax where id=? and groupid=? and date=?";
            Connection con = Conn.getConnection();
            PreparedStatement ptmt = Conn.getPtmt(con,sql);
            ptmt.setLong(1,id);
            ptmt.setLong(2,groupid);
            ptmt.setDate(3,Conn.getdate());
            ResultSet rs = ptmt.executeQuery();
            if(rs.next()){
                double speed = rs.getDouble("speed");
                if(speed<Com[0]){
                    sql = "update groupsaiwenmax set speed=?,keyspeed=?,keylength=? where id=? and groupid=? and date=?";
                    ptmt = Conn.getPtmt(con,sql);
                    ptmt.setDouble(1,Com[0]);
                    ptmt.setDouble(2,Com[1]);
                    ptmt.setDouble(3,Com[2]);
                    ptmt.setLong(4,id);
                    ptmt.setLong(5,groupid);
                    ptmt.setDate(6,Conn.getdate());
                    ptmt.execute();
                    System.out.println("更改成绩"+id+" 群"+groupid);
                }
            }
            else{
                sql = "insert into groupsaiwenmax values(?,?,?,?,?,?)";
                ptmt = Conn.getPtmt(con,sql);
                ptmt.setLong(1,groupid);
                ptmt.setLong(2,id);
                ptmt.setDate(3,Conn.getdate());
                ptmt.setDouble(4,Com[0]);
                ptmt.setDouble(5,Com[1]);
                ptmt.setDouble(6,Com[2]);
                ptmt.execute();
                System.out.println("添加成绩"+id+" 群"+groupid);
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static  void AddRobotHistory(Long id,Long groupid,double[] Com){
        try {
            String sql1 = "insert into robothistory values(?,?,?,?,?,default,?)";
            String sql2 = "SELECT * FROM robotclient where id=" + id;
            String sql3 = "insert into robotclient values(?,default,?,?,?,?,default)";
            String sql4 = "update robotclient set n=?,speedaver=?,keyspeedaver=?,keylengthaver=? where id=?";
            Connection con = Conn.getConnection();
            ResultSet rs = Conn.getStmtSet(con,sql2);
            PreparedStatement ptmt;
            if (rs.next()){
                ptmt = Conn.getPtmt(con,sql4);
                int n = rs.getInt("n");
                double speedaver = rs.getDouble("speedaver");
                double keyspeedaver = rs.getDouble("keyspeedaver");
                double keylengthaver = rs.getDouble("keylengthaver");
                ptmt.setInt(1,n+1);
                ptmt.setDouble(2,(speedaver*n+Com[0])/(n+1));
                ptmt.setDouble(3,(keyspeedaver*n+Com[1])/(n+1));
                ptmt.setDouble(4,(keylengthaver*n+Com[2])/(n+1));
                ptmt.setLong(5,id);
                ptmt.execute();
            } else {
                ptmt = Conn.getPtmt(con,sql3);
                ptmt.setLong(1,id);
                ptmt.setInt(2,1);
                ptmt.setDouble(3,Com[0]);
                ptmt.setDouble(4,Com[1]);
                ptmt.setDouble(5,Com[2]);
                ptmt.execute();
            }
            ptmt = Conn.getPtmt(con,sql1);
            ptmt.setLong(1,id);
            ptmt.setLong(2,groupid);
            ptmt.setDouble(3,Com[0]);
            ptmt.setDouble(4,Com[1]);
            ptmt.setDouble(5,Com[2]);
            ptmt.setDate(6,Conn.getdate());
            ptmt.execute();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void AddGroupCheatNum(int num,long id){
        String sql = "select * from robotclient where id="+id;
        Connection con = Conn.getConnection();
        try{
            ResultSet rs = Conn.getStmtSet(con,sql);
            if(rs.next()){
                sql = "update robotclient set cheatnum="
                        +(rs.getInt("cheatnum")+num)
                        +" where id="+id;
                Conn.execute(con,sql);
            }else{
                sql = "insert into robotclient values(?,default,default,default,default,default,default)";
                PreparedStatement ptmt = Conn.getPtmt(con,sql);
                ptmt.setLong(1,id);
                ptmt.execute();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String addRobotSaiwenMath(Long id,Long groupid,double[] Com,String name,String typeandaid){
        String message = "";
        String []s = typeandaid.split("%");
        String type = s[0];
        int aid = Integer.valueOf(s[1]);
        try{
            String username = initGroupList.GroupMemberCardMap.get(groupid).get(id);
            if (username.equals("") || username == null)
//              String
                      username = name;
            String sql = "select * from robotsaiwenchengji where aid=? and id=? and type=?";
            Connection con = Conn.getConnection();
            PreparedStatement ptmt = Conn.getPtmt(con,sql);
            ptmt.setInt(1,aid);
            ptmt.setLong(2,id);
            ptmt.setString(3,type);
            ResultSet rs = ptmt.executeQuery();
            if(rs.next()){
                double speed = rs.getDouble("sudu");
                int num = rs.getInt("typenum");
                if(speed<Com[0]) {
                    sql = "update robotsaiwenchengji set sudu=?,keyspeed=?,keylength=?,groupid=?,typenum=?,name=?,del=?,deletetext=?,sel=?,mistake=?,rightkeyper=? where id=? and aid=? and type=?";
                    ptmt = Conn.getPtmt(con, sql);
                    ptmt.setDouble(1, Com[0]);
                    ptmt.setDouble(2, Com[1]);
                    ptmt.setDouble(3, Com[2]);
                    ptmt.setLong(4, groupid);
                    ptmt.setInt(5, rs.getInt("typenum") + 1);
                    ptmt.setString(6, username);
                    ptmt.setInt(7, (int) Com[3]);
                    ptmt.setInt(8, (int) Com[4]);
                    ptmt.setInt(9, (int) Com[5]);
                    ptmt.setInt(10, (int) Com[6]);
                    ptmt.setDouble(11, Com[7]);
                    ptmt.setLong(12, id);
                    ptmt.setInt(13, aid);
                    ptmt.setString(14,type);
                    System.out.println("更改联赛成绩" + id + " " + groupid);
                    ptmt.execute();
                }else {
                    sql = "update robotsaiwenchengji set typenum=? where id=? and aid=? and type=?";
                    ptmt = Conn.getPtmt(con,sql);
                    ptmt.setInt(1,num+1);
                    ptmt.setLong(2,id);
                    ptmt.setInt(3,aid);
                    ptmt.setString(4,type);
                    ptmt.execute();
                }
            }else {
                sql = "insert into robotsaiwenchengji values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ptmt = Conn.getPtmt(con, sql);
                ptmt.setLong(1, id);
                ptmt.setLong(2, groupid);
                ptmt.setDouble(3, Com[0]);
                ptmt.setDouble(4, Com[1]);
                ptmt.setDouble(5, Com[2]);
                ptmt.setInt(6, 1);
                ptmt.setString(7, username);
                ptmt.setInt(8, (int) Com[3]);
                ptmt.setInt(9, (int) Com[4]);
                ptmt.setInt(10, (int) Com[5]);
                ptmt.setInt(11, (int) Com[6]);
                ptmt.setDouble(12, Com[7]);
                ptmt.setInt(13, aid);
                ptmt.setString(14,type);
                ptmt.execute();
            }
            con.close();
            message = aid + "编号成绩提交成绩成功";
        }catch (Exception e){
            message = aid+"编号成绩提交成绩失败，请备份成绩与管理员联系。";
            e.printStackTrace();
        }
        return message;
    }
}
