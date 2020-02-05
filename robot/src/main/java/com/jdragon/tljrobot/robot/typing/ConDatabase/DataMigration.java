package com.jdragon.tljrobot.robot.typing.ConDatabase;

import com.alibaba.nacos.common.util.Md5Utils;

import java.sql.*;
import java.util.HashMap;

/**
 * Create by Jdragon on 2020.02.03
 */
public class DataMigration {
    public static void main(String[] args) throws SQLException {

    }
    public void robot_group_match() throws SQLException {
        String selectGroupSaiWen = "select * from groupsaiwen";
        Connection oldCon = Conn.getConnection();
        Connection newCon = ConnNew.getConnection();

        String selectArticleId = "select id from all_article where title=? and content=?";
        PreparedStatement selectArticleIdPtmt = ConnNew.getPtmt(newCon,selectArticleId);

        String insertArticle = "insert into all_article (title,content) values (?,?)";
        PreparedStatement insertArticlePtmt = ConnNew.getPtmtHaveKey(newCon,insertArticle);

        String insertGroupMatch = "insert into robot_group_match (holdDate,articleId,groupId) values (?,?,?)";
        PreparedStatement insertGroupMatchPtmt = ConnNew.getPtmt(newCon,insertGroupMatch);

        ResultSet groupSaiWenRs = Conn.getStmtSet(oldCon,selectGroupSaiWen);
        int i = 0;
        while(groupSaiWenRs.next()){
            long groupId = groupSaiWenRs.getLong("groupid");
            Date holdDate = groupSaiWenRs.getDate("saiwendate");
            String content = groupSaiWenRs.getString("saiwen");
            String title = groupSaiWenRs.getString("title");
            selectArticleIdPtmt.setString(1,title);
            selectArticleIdPtmt.setString(2,content);
            ResultSet selectArticleIdRs = selectArticleIdPtmt.executeQuery();
            int articleId;
            if(selectArticleIdRs.next()){
                articleId = selectArticleIdRs.getInt(1);
            }else{
                insertArticlePtmt.setString(1,title);
                insertArticlePtmt.setString(2,content);
                insertArticlePtmt.executeUpdate();
                ResultSet insertArticleRs = insertArticlePtmt.getGeneratedKeys();
                if(insertArticleRs.next()){
                    articleId = insertArticleRs.getInt(1);
                }else{
                    System.out.println("插入失败");
                    continue;
                }
            }
            insertGroupMatchPtmt.setDate(1,holdDate);
            insertGroupMatchPtmt.setInt(2,articleId);
            insertGroupMatchPtmt.setLong(3,groupId);
            System.out.println(i+=insertGroupMatchPtmt.executeUpdate());
        }
    }
    public void robot_match_ach() throws SQLException {
        Connection oldCon = Conn.getConnection();
        Connection newCon = ConnNew.getConnection();

        String insertRobotHistory = "insert into robot_history (" +
                "qq,groupId,typeDate,speed,keySpeed," +
                "keyLength,deleteText,deleteNum,mistake,repeatNum," +
                "keyAccuracy,isMatch,isFirst) " +
                "values (" +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?)";
        PreparedStatement insertRobotHistoryPtmt = ConnNew.getPtmt(newCon,insertRobotHistory);

        String selectAllGroupSaiWenChengji = "select * from allgroupsaiwenchengji";
        ResultSet allGroupSaiWenChengjiRs = Conn.getStmtSet(oldCon,selectAllGroupSaiWenChengji);
        int i = 0;
        while(allGroupSaiWenChengjiRs.next()){
            long qq = allGroupSaiWenChengjiRs.getLong("id");
            long groupId = allGroupSaiWenChengjiRs.getLong("groupid");
            Date typeDate = allGroupSaiWenChengjiRs.getDate("saiwendate");
            double speed = allGroupSaiWenChengjiRs.getDouble("sudu");
            double keySpeed = allGroupSaiWenChengjiRs.getDouble("keyspeed");
            double keyLength = allGroupSaiWenChengjiRs.getDouble("keylength");
            int deleteNum = allGroupSaiWenChengjiRs.getInt("del");
            int deleteText = allGroupSaiWenChengjiRs.getInt("deletetext");
            int repeatNum = allGroupSaiWenChengjiRs.getInt("sel");
            int mistake = allGroupSaiWenChengjiRs.getInt("mistake");
            double keyAccuracy = allGroupSaiWenChengjiRs.getDouble("rightkeyper");
            insertRobotHistoryPtmt.setLong(1,qq);
            insertRobotHistoryPtmt.setLong(2,groupId);
            insertRobotHistoryPtmt.setDate(3,typeDate);
            insertRobotHistoryPtmt.setDouble(4,speed);
            insertRobotHistoryPtmt.setDouble(5,keySpeed);
            insertRobotHistoryPtmt.setDouble(6,keyLength);
            insertRobotHistoryPtmt.setInt(7,deleteText);
            insertRobotHistoryPtmt.setInt(8,deleteNum);
            insertRobotHistoryPtmt.setInt(9,mistake);
            insertRobotHistoryPtmt.setInt(10,repeatNum);
            insertRobotHistoryPtmt.setDouble(11,keyAccuracy);
            insertRobotHistoryPtmt.setBoolean(12, true);
            insertRobotHistoryPtmt.setBoolean(13, true);
            System.out.println(i+=insertRobotHistoryPtmt.executeUpdate());
        }
    }
    public void robot_match() throws SQLException {
        String selectAllGroupSaiWen = "select * from allgroupsaiwen";
        Connection oldCon = Conn.getConnection();
        Connection newCon = ConnNew.getConnection();

        String selectArticleId = "select id from all_article where title=? and content=?";
        PreparedStatement selectArticleIdPtmt = ConnNew.getPtmt(newCon,selectArticleId);

        String insertArticle = "insert into all_article (title,content) values(?,?)";
        PreparedStatement insertArticlePtmt = ConnNew.getPtmtHaveKey(newCon,insertArticle);

        String insertRobotMatch = "insert into robot_match (holdDate,articleId,author) values (?,?,?)";
        PreparedStatement insertRobotMatchPtmt = ConnNew.getPtmt(newCon,insertRobotMatch);

        ResultSet allGroupSaiWenRs = Conn.getStmtSet(oldCon,selectAllGroupSaiWen);
        int i = 0;
        while(allGroupSaiWenRs.next()){
            Date holdDate = allGroupSaiWenRs.getDate("saiwendate");
            String title = allGroupSaiWenRs.getString("title");
            String content = allGroupSaiWenRs.getString("saiwen");
            String author = allGroupSaiWenRs.getString("author");

            selectArticleIdPtmt.setString(1,title);
            selectArticleIdPtmt.setString(2,content);
            ResultSet selectArticleIdRs = selectArticleIdPtmt.executeQuery();
            int articleId;
            if(selectArticleIdRs.next()){
                articleId = selectArticleIdRs.getInt(1);
            }else{
                insertArticlePtmt.setString(1,title);
                insertArticlePtmt.setString(2,content);
                insertArticlePtmt.executeUpdate();
                ResultSet insertArticlePtmtRs = insertArticlePtmt.getGeneratedKeys();
                if(insertArticlePtmtRs.next()){
                    articleId = insertArticlePtmtRs.getInt(1);
                }else{
                    System.out.println("文章插入失败");
                    continue;
                }
            }
            insertRobotMatchPtmt.setDate(1,holdDate);
            insertRobotMatchPtmt.setInt(2,articleId);
            insertRobotMatchPtmt.setString(3,author);
            System.out.println(i+=insertRobotMatchPtmt.executeUpdate());
        }
    }
    public void history() throws SQLException {
        HashMap<String,Integer> userCache = new HashMap<>();
        String selectUser = "select id,username from tlj_user";
        Connection oldCon = Conn.getConnection();
        Connection newCon = ConnNew.getConnection();
        ResultSet userRs = ConnNew.getStmtSet(newCon,selectUser);
        while(userRs.next()){
            userCache.put(userRs.getString("username"),userRs.getInt("id"));
        }

        String insertHistory = "insert into tlj_history " +
                "(userId,articleId,typeDate,speed,keySpeed," +
                "keyLength,number,deleteText,deleteNum,mistake," +
                "repeatNum,keyAccuracy,keyMethod,wordRate,time," +
                "paragraph,isMobile) " +
                "values(" +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?)";

        String selectArticleId = "select id from all_article where content=?";

        String insertArticle = "insert into all_article (title,content) values(?,?)";

        PreparedStatement insertHistoryPtmt = ConnNew.getPtmt(newCon,insertHistory);
        PreparedStatement selectArticleIdPtmt = ConnNew.getPtmt(newCon,selectArticleId);
        PreparedStatement insertArticlePtmt = ConnNew.getPtmtHaveKey(newCon,insertArticle);

        String selectHistory = "select * from history";
        ResultSet historyRs = Conn.getStmtSet(oldCon,selectHistory);
        int i = 0;
        while(historyRs.next()){
            String username = historyRs.getString("name");
            if(userCache.get(username)==null)continue;;
            Date typeDate = historyRs.getDate("date");
            double speed = historyRs.getDouble("sudu");
            double keySpeed = historyRs.getDouble("key");
            double keyLength = historyRs.getDouble("keylength");
            int number = historyRs.getInt("number");
            int deleteText = historyRs.getInt("deletetext");
            int deleteNum  = historyRs.getInt("delete");
            int mistake = historyRs.getInt("mistake");
            int repeatNum = historyRs.getInt("repeat");
            double keyAccuracy = historyRs.getDouble("Keyaccuracy");
            double keyMethod = historyRs.getDouble("Keymethod");
            double wordRate = historyRs.getDouble("dacilv");
            double time = historyRs.getDouble("time");
            String content = historyRs.getString("wenben");
            int paragraph = historyRs.getInt("duan");



            selectArticleIdPtmt.setString(1,content);
            ResultSet selectArticleIdRs = selectArticleIdPtmt.executeQuery();
            if(selectArticleIdRs.next()){
                int articleId = selectArticleIdRs.getInt(1);
                insertHistoryPtmt.setInt(1,userCache.get(username));
                insertHistoryPtmt.setInt(2,articleId);
                insertHistoryPtmt.setDate(3,typeDate);
                insertHistoryPtmt.setDouble(4,speed);
                insertHistoryPtmt.setDouble(5,keySpeed);
                insertHistoryPtmt.setDouble(6,keyLength);
                insertHistoryPtmt.setInt(7,number);
                insertHistoryPtmt.setInt(8,deleteText);
                insertHistoryPtmt.setInt(9,deleteNum);
                insertHistoryPtmt.setInt(10,mistake);
                insertHistoryPtmt.setInt(11,repeatNum);
                insertHistoryPtmt.setDouble(12,keyAccuracy);
                insertHistoryPtmt.setDouble(13,keyMethod);
                insertHistoryPtmt.setDouble(14,wordRate);
                insertHistoryPtmt.setDouble(15,time);
                insertHistoryPtmt.setInt(16,paragraph);
                insertHistoryPtmt.setInt(17,0);
                System.out.println(i+=insertHistoryPtmt.executeUpdate());
            }else{
                insertArticlePtmt.setString(1,"无");
                insertArticlePtmt.setString(2,content);
                insertArticlePtmt.executeUpdate();
                ResultSet insertArticleRs = insertArticlePtmt.getGeneratedKeys();
                if(insertArticleRs.next()){
                    int articleId = insertArticleRs.getInt(1);
                    insertHistoryPtmt.setInt(1,userCache.get(username));
                    insertHistoryPtmt.setInt(2,articleId);
                    insertHistoryPtmt.setDate(3,typeDate);
                    insertHistoryPtmt.setDouble(4,speed);
                    insertHistoryPtmt.setDouble(5,keySpeed);
                    insertHistoryPtmt.setDouble(6,keyLength);
                    insertHistoryPtmt.setInt(7,number);
                    insertHistoryPtmt.setInt(8,deleteText);
                    insertHistoryPtmt.setInt(9,deleteNum);
                    insertHistoryPtmt.setInt(10,mistake);
                    insertHistoryPtmt.setInt(11,repeatNum);
                    insertHistoryPtmt.setDouble(12,keyAccuracy);
                    insertHistoryPtmt.setDouble(13,keyMethod);
                    insertHistoryPtmt.setDouble(14,wordRate);
                    insertHistoryPtmt.setDouble(15,time);
                    insertHistoryPtmt.setInt(16,paragraph);
                    insertHistoryPtmt.setInt(17,0);
                    System.out.println(i+=insertHistoryPtmt.executeUpdate());
                }else{
                    System.out.println("插入失败");
                }
            }
        }
    }
    public void tljMatchAch() throws SQLException {
        HashMap<String,Integer> userCache = new HashMap<>();
        String selectUser = "select id,username from tlj_user";
        Connection oldCon = Conn.getConnection();
        Connection newCon = ConnNew.getConnection();
        ResultSet userRs = ConnNew.getStmtSet(newCon,selectUser);
        while(userRs.next()){
            userCache.put(userRs.getString("username"),userRs.getInt("id"));
        }

        String insertTljMatchHistory = "insert into tlj_history " +
                "(userId,articleId,typeDate,speed,keySpeed," +
                "keyLength,number,deleteText,deleteNum,mistake," +
                "repeatNum,keyAccuracy,keyMethod,wordRate,time," +
                "paragraph,isMobile) " +
                "values(" +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?)";

        String selectArticleId = "select articleId from tlj_match where holdDate=?";

        PreparedStatement insertTljMatchHistoryPtmt = ConnNew.getPtmt(newCon,insertTljMatchHistory);
        PreparedStatement selectArticleIdPtmt = ConnNew.getPtmt(newCon,selectArticleId);

        String selectSaiwenchengji = "select * from saiwenchengji";
        ResultSet saiwenchengjiRs = Conn.getStmtSet(oldCon,selectSaiwenchengji);
        int i = 0;
        while(saiwenchengjiRs.next()){
            String username = saiwenchengjiRs.getString("name");
            if(userCache.get(username)==null)continue;;
            Date typeDate = saiwenchengjiRs.getDate("date");
            double speed = saiwenchengjiRs.getDouble("sudu");
            double keySpeed = saiwenchengjiRs.getDouble("key");
            double keyLength = saiwenchengjiRs.getDouble("keylength");
            int number = saiwenchengjiRs.getInt("number");
            int deleteText = saiwenchengjiRs.getInt("deletetext");
            int deleteNum  = saiwenchengjiRs.getInt("delete");
            int mistake = saiwenchengjiRs.getInt("mistake");
            int repeatNum = saiwenchengjiRs.getInt("repeat");
            double keyAccuracy = saiwenchengjiRs.getDouble("Keyaccuracy");
            double keyMethod = saiwenchengjiRs.getDouble("Keymethod");
            double wordRate = saiwenchengjiRs.getDouble("dacilv");
            double time = saiwenchengjiRs.getDouble("time");

            selectArticleIdPtmt.setDate(1,typeDate);
            ResultSet selectArticleIdRs = selectArticleIdPtmt.executeQuery();
            if(selectArticleIdRs.next()){
                int articleId = selectArticleIdRs.getInt(1);
                insertTljMatchHistoryPtmt.setInt(1,userCache.get(username));
                insertTljMatchHistoryPtmt.setInt(2,articleId);
                insertTljMatchHistoryPtmt.setDate(3,typeDate);
                insertTljMatchHistoryPtmt.setDouble(4,speed);
                insertTljMatchHistoryPtmt.setDouble(5,keySpeed);
                insertTljMatchHistoryPtmt.setDouble(6,keyLength);
                insertTljMatchHistoryPtmt.setInt(7,number);
                insertTljMatchHistoryPtmt.setInt(8,deleteText);
                insertTljMatchHistoryPtmt.setInt(9,deleteNum);
                insertTljMatchHistoryPtmt.setInt(10,mistake);
                insertTljMatchHistoryPtmt.setInt(11,repeatNum);
                insertTljMatchHistoryPtmt.setDouble(12,keyAccuracy);
                insertTljMatchHistoryPtmt.setDouble(13,keyMethod);
                insertTljMatchHistoryPtmt.setDouble(14,wordRate);
                insertTljMatchHistoryPtmt.setDouble(15,time);
                insertTljMatchHistoryPtmt.setInt(16,0);
                insertTljMatchHistoryPtmt.setInt(17,0);
                insertTljMatchHistoryPtmt.addBatch();
                System.out.println(i+=insertTljMatchHistoryPtmt.executeUpdate());
            }
        }
    }
    public void tljMatch() throws SQLException {
        String selectUser = "select * from everydaysaiwen";
        Connection oldCon = Conn.getConnection();
        Connection newCon = ConnNew.getConnection();

        ResultSet resultSet = Conn.getStmtSet(oldCon,selectUser);

        String selectArticlePrefix = "select id from all_article where content=?";

        String insertArticle = "insert into all_article (title,content) values(?,?)";

        String insertMatch = "insert into tlj_match (holdDate,articleId,author) values(?,?,?)";

        PreparedStatement selectArticlePtmt = ConnNew.getPtmt(newCon,selectArticlePrefix);
        PreparedStatement insertMatchPtmt = ConnNew.getPtmt(newCon,insertMatch);
        PreparedStatement insertArticlePtmt = ConnNew.getPtmtHaveKey(newCon,insertArticle);
        int i = 0;
        while(resultSet.next()){
            Date date = resultSet.getDate("saiwendate");
            String content = resultSet.getString("saiwen");
            String author = resultSet.getString("author");
            String title = date+"日生稿赛";
            selectArticlePtmt.setString(1,content);
            ResultSet articleRs = selectArticlePtmt.executeQuery();
            if(articleRs.next()){
                int articleId = articleRs.getInt("id");
                insertMatchPtmt.setDate(1,date);
                insertMatchPtmt.setInt(2,articleId);
                insertMatchPtmt.setString(3,author);
                insertMatchPtmt.executeUpdate();
            }else{
                insertArticlePtmt.setString(1,title);
                insertArticlePtmt.setString(2,content);
                insertArticlePtmt.executeUpdate();
                ResultSet insertArticleRs = insertArticlePtmt.getGeneratedKeys();
                if(insertArticleRs.next()){
                    int articleId = insertArticleRs.getInt(1);
                    insertMatchPtmt.setDate(1,date);
                    insertMatchPtmt.setInt(2,articleId);
                    insertMatchPtmt.setString(3,author);
                    insertMatchPtmt.executeUpdate();
                }else{System.out.println("插入赛文错误");}
            }
            System.out.println(++i);
        }
    }
    public void user() throws SQLException {
        String selectUser = "select * from client";
        Connection oldCon = Conn.getConnection();
        ResultSet resultSet = Conn.getStmtSet(oldCon,selectUser);

        String insertUser = "insert into tlj_user (username,password,num,rightNum,misNum,dateNum,regDate,lastLoginDate) " +
                "values(?,?,?,?,?,?,?,?)";
        Connection newCon = ConnNew.getConnection();
        PreparedStatement newPtmt = ConnNew.getPtmt(newCon,insertUser);
        int i=0;
        while(resultSet.next()){
            newPtmt.setString(1,resultSet.getString("username"));
            String password = resultSet.getString("password");
            if(password=="")password="12345678";
            newPtmt.setString(2, Md5Utils.getMD5(password.getBytes()));
            newPtmt.setInt(3,resultSet.getInt("num"));
            newPtmt.setInt(4,resultSet.getInt("rightnum"));
            newPtmt.setInt(5,resultSet.getInt("misnum"));
            newPtmt.setInt(6,resultSet.getInt("datenum"));
            newPtmt.setDate(7,resultSet.getDate("zhucedate"));
            newPtmt.setDate(8,resultSet.getDate("lastdate"));
            i += newPtmt.executeUpdate();
            System.out.println(i);

        }
    }
}
