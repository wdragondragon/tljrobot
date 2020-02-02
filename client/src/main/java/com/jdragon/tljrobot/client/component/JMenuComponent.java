package com.jdragon.tljrobot.client.component;

import com.jdragon.tljrobot.client.event.FArea.ChangeQQGroup;
import com.jdragon.tljrobot.client.event.FArea.QQGetArticle;
import com.jdragon.tljrobot.client.event.FArea.Replay;
import com.jdragon.tljrobot.client.event.FArea.SendAchievement;
import com.jdragon.tljrobot.client.event.Other.ClipboardGetArticle;
import com.jdragon.tljrobot.client.event.online.HistoryEvent;
import com.jdragon.tljrobot.client.listener.common.ArticleTreeListener;
import com.jdragon.tljrobot.client.listener.common.BuildChooseFile;
import com.jdragon.tljrobot.client.listener.common.MixListener;
import com.jdragon.tljrobot.client.window.HelpDialog;
import com.jdragon.tljrobot.client.window.HistoryDialog;
import com.jdragon.tljrobot.client.window.LogonDialog;
import com.jdragon.tljrobot.client.window.SetDialog;
import lombok.Data;

import javax.swing.*;
import java.awt.event.KeyEvent;

@Data
public class JMenuComponent {
    private static JMenuComponent jMenuComponent = null;
    private JMenuComponent(){}
    public static JMenuComponent getInstance() {
        if(jMenuComponent==null)jMenuComponent = new JMenuComponent();
        return jMenuComponent;
    }
    public JMenu menu, base, onlineMenu, otherMenu, rankingMenu, sendArticleMenu;
    public JMenuItem Login;
    public JMenuItem sendAchievement;
    public JMenuItem sendArticle;
    public JMenuItem replay;
    public JMenuItem QQGetArticleItem;
    public JMenuItem groupChanging;
    public JMenuItem getArticleByClipboard;
    // JMenuItem jjmu;
    public JMenuItem moreSetUp;
    public JMenuItem battleOnline;
    public JMenuItem help;
    public JMenuItem helpAuthor;
    public JMenuItem randomArticle;
    public JMenuItem allNumberRanking, todayNumberRanking, matchRanking;
    public JMenuItem createCodeTable;
    public JMenuItem getHistory;
    public JMenuItem friendSys;
    public JMenuItem everydayMatch;
    public JMenuItem everydayMatchRanking;
    public JMenuItem reset;
    public JMenuItem buildMatch;
    public JMenuItem gatherTogether;

    public JMenuItem nonOrder;
    public JMenuItem orderNextParagraph;
    public JMenuItem save;
//    public JMenuItem extractNextParagraph;
    public JMenuItem email;
    public JMenuItem checkCode;
    public JMenuItem openCheat;

    public JMenuItem resert;

    public JMenuItem lookPlay;
    public JMenuItem check;
    public JMenuItem nextEnglish;
    public JMenuItem update;
    public JMenu getMenu(){
        menu = new JMenu("菜单");
        initItem();
        addListener();
        setAccelerator();
        addItem();
        return menu;
    }
    public void initItem(){
        base = new JMenu("基本操作");
        onlineMenu = new JMenu("联网操作");
        otherMenu = new JMenu("其他");
        rankingMenu = new JMenu("排名");
        sendArticleMenu = new JMenu("发文操作");

        allNumberRanking = new JMenuItem("总跟打排名");
        todayNumberRanking = new JMenuItem("日跟打排名");
        matchRanking = new JMenuItem("赛文平均成绩排名");
        everydayMatchRanking = new JMenuItem("每日赛文排名");

        Login = new JMenuItem("登录");
        sendAchievement = new JMenuItem("发送成绩 F1");
        sendArticle = new JMenuItem("发文F2");
        replay = new JMenuItem("重打F3");
        QQGetArticleItem = new JMenuItem("载文 F4");
        groupChanging = new JMenuItem("换群 F5");

        orderNextParagraph = new JMenuItem("下一段");
        save = new JMenuItem("保存进度");
//        extractNextParagraph = new JMenuItem("抽取下一段");
        nonOrder = new JMenuItem("该段乱序");

        getArticleByClipboard = new JMenuItem("剪贴板载文");
        randomArticle = new JMenuItem("随机一文");

        createCodeTable = new JMenuItem("生成码表");
        moreSetUp = new JMenuItem("更多设置 alt+Z");
        battleOnline = new JMenuItem("在线对战");
        help = new JMenuItem("使用帮助");
        helpAuthor = new JMenuItem("协助作者");
        getHistory = new JMenuItem("跟打记录");
        friendSys = new JMenuItem("好友系统");
        everydayMatch = new JMenuItem("每日赛文");
        buildMatch = new JMenuItem("创建比赛");
        gatherTogether = new JMenuItem("共聚一堂");
        reset = new JMenuItem("复位");
        email = new JMenuItem("绑定邮箱");
        checkCode = new JMenuItem("检查编码");
        openCheat = new JMenuItem("隐藏功能");
        resert = new JMenuItem("错位复位");
        update = new JMenuItem("更新");

        lookPlay = new JMenuItem("成绩提交");
        check = new JMenuItem("看打检验");

        nextEnglish = new JMenuItem("英词下一段");
    }
    private void addItem(){
        sendArticleMenu.add(nonOrder);
        sendArticleMenu.add(orderNextParagraph);
//        sendArticleMenu.add(extractNextParagraph);
//        sendArticleMenu.add(nextEnglish);
        sendArticleMenu.add(save);

        rankingMenu.add(allNumberRanking);
        rankingMenu.add(todayNumberRanking);
        rankingMenu.add(matchRanking);
        rankingMenu.add(everydayMatchRanking);

        base.add(sendAchievement);
        base.add(sendArticle);
        base.add(replay);
        base.add(QQGetArticleItem);
        base.add(groupChanging);
        base.add(getArticleByClipboard);
//        base.add(checkCode);
        base.add(sendArticleMenu);
//        base.add(lookPlay);
//        base.add(check);

//        onlineMenu.add(rankingMenu);
//        onlineMenu.add(battleOnline);
        onlineMenu.add(getHistory);
        onlineMenu.add(everydayMatch);
//        onlineMenu.add(email);
//        onlineMenu.add(friendSys);
//        onlineMenu.add(buildMatch);
//        onlineMenu.add(gatherTogether);

        // other.add(jjmu);
        otherMenu.add(createCodeTable);
//        otherMenu.add(randomArticle);

        menu.add(Login);
        menu.add(base);
        menu.add(onlineMenu);
        menu.add(otherMenu);
//
        menu.add(help);
//        menu.add(helpAuthor);
        menu.add(moreSetUp);
//        menu.add(openCheat);
//        menu.add(resert);
//        menu.add(update);
    }
    public void setAccelerator(){
        moreSetUp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                KeyEvent.ALT_MASK));
        orderNextParagraph.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
                KeyEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK));
        nonOrder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,KeyEvent.CTRL_MASK));
        getArticleByClipboard.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK));
    }
    public void addListener(){
        Login.addActionListener(e-> LogonDialog.getInstance().setVisible(true));
        sendAchievement.addActionListener(e-> SendAchievement.start());
        sendArticle.addActionListener(e->{
//            ShareArticle.start();
        });
        replay.addActionListener(e-> Replay.start());
        QQGetArticleItem.addActionListener(e -> QQGetArticle.start());
        groupChanging.addActionListener(e-> ChangeQQGroup.start());

        moreSetUp.addActionListener(e-> SetDialog.getInstance().setVisible(true));
        everydayMatch.addActionListener(e-> HistoryEvent.getMatch());
        getHistory.addActionListener(e-> HistoryDialog.getInstance().setVisible(true));
        orderNextParagraph.addActionListener(ArticleTreeListener.getInstance());
        save.addActionListener(ArticleTreeListener.getInstance());
        nonOrder.addActionListener(MixListener.getInstance());
        createCodeTable.addActionListener(new BuildChooseFile());
        getArticleByClipboard.addActionListener(e-> ClipboardGetArticle.start());
        help.addActionListener(e-> HelpDialog.showHelp());
    }
}
