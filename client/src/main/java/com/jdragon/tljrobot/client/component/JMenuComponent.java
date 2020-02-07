package com.jdragon.tljrobot.client.component;

import com.jdragon.tljrobot.client.config.FinalConfig;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.config.OnlineConfig;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.FArea.*;
import com.jdragon.tljrobot.client.event.online.HistoryEvent;
import com.jdragon.tljrobot.client.event.other.ListenPlay;
import com.jdragon.tljrobot.client.event.other.SwitchFollowPlay;
import com.jdragon.tljrobot.client.event.other.SwitchListenPlay;
import com.jdragon.tljrobot.client.event.other.SwitchWatchPlay;
import com.jdragon.tljrobot.client.listener.common.ArticleTreeListener;
import com.jdragon.tljrobot.client.listener.common.BuildChooseFile;
import com.jdragon.tljrobot.client.listener.common.MixListener;
import com.jdragon.tljrobot.client.listener.common.Typing;
import com.jdragon.tljrobot.client.utils.common.Clipboard;
import com.jdragon.tljrobot.client.window.*;
import com.jdragon.tljrobot.tljutils.SystemUtil;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.jdragon.tljrobot.client.component.SwingSingleton.TypingText;

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
    public JMenuItem moreRanking;

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

    public JMenu switchingMode;//切换模式
    public JMenuItem watchMode = new JMenuItem("看打模式 ctrl+K");
    public JMenuItem listenMode = new JMenuItem("听打模式 ctrl+Q");
    public JMenuItem followMode = new JMenuItem("跟打模式 ctrl+G");


    public JMenuItem soundRecordPlay = new JMenuItem("听打选择文件");//
    public JMenu getMenu(){
        menu = new JMenu("菜单");
        initItem();
        addListener();
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
        moreRanking = new JMenuItem("字数排名");

        Login = new JMenuItem("登录");
        sendAchievement = new JMenuItem("发送成绩 F1");
        sendArticle = new JMenuItem("发文F2");
        replay = new JMenuItem("重打F3");
        QQGetArticleItem = new JMenuItem("载文 F4");
        groupChanging = new JMenuItem("换群 F5");

        orderNextParagraph = new JMenuItem("下一段 ctrl+P");
        save = new JMenuItem("保存进度 ctrl+S");
//        extractNextParagraph = new JMenuItem("抽取下一段");
        nonOrder = new JMenuItem("该段乱序 ctrl+L");

        getArticleByClipboard = new JMenuItem("剪贴板载文 ctrl+E");
        randomArticle = new JMenuItem("随机一文");

        createCodeTable = new JMenuItem("生成码表");
        moreSetUp = new JMenuItem("更多设置 ctrl+Z");
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
        update = new JMenuItem("版本"+ FinalConfig.VERSION+" 最新" + OnlineConfig.NEW_VERSION);

        lookPlay = new JMenuItem("成绩提交 ctrl+enter");
        check = new JMenuItem("看打检验");

        nextEnglish = new JMenuItem("英词下一段");

        switchingMode = new JMenu("当前模式："+ LocalConfig.typingPattern);

    }
    private void addItem(){
        sendArticleMenu.add(nonOrder);
        sendArticleMenu.add(orderNextParagraph);
//        sendArticleMenu.add(extractNextParagraph);
//        sendArticleMenu.add(nextEnglish);
        sendArticleMenu.add(save);

//        rankingMenu.add(allNumberRanking);
//        rankingMenu.add(todayNumberRanking);
//        rankingMenu.add(matchRanking);

        rankingMenu.add(everydayMatchRanking);
        rankingMenu.add(moreRanking);

        base.add(sendAchievement);
        base.add(sendArticle);
        base.add(replay);
        base.add(QQGetArticleItem);
        base.add(groupChanging);
        base.add(getArticleByClipboard);
//        base.add(checkCode);
        base.add(sendArticleMenu);
        base.add(lookPlay);
//        base.add(check);

        onlineMenu.add(rankingMenu);
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

        switchingMode.add(followMode);
        switchingMode.add(watchMode);
        switchingMode.add(listenMode);

        menu.add(Login);
        menu.add(base);
        menu.add(onlineMenu);
        menu.add(otherMenu);
//
        menu.add(help);
//        menu.add(helpAuthor);
        menu.add(moreSetUp);
        menu.add(switchingMode);
//        menu.add(openCheat);
//        menu.add(resert);
        menu.add(update);
        menu.add(soundRecordPlay);
    }
    public void addListener(){
        Login.addActionListener(e-> LogonDialog.getInstance().setVisible(true));
        sendAchievement.addActionListener(e-> SendAchievement.start());
        sendArticle.addActionListener(e-> SendArticle.start());
        replay.addActionListener(e-> Replay.start());
        QQGetArticleItem.addActionListener(e -> QQGetArticle.start());
        groupChanging.addActionListener(e-> ChangeQQGroup.start());

        moreSetUp.addActionListener(e-> SetDialog.getInstance().setVisible(true));
        everydayMatch.addActionListener(e-> {
            if(!UserState.loginState){
                JOptionPane.showMessageDialog(MainFra.getInstance(),"请先登录");
                return;
            }
            int n = JOptionPane.showConfirmDialog(null, "日赛一天只能获取一次，请问做好准备跟打?", "日赛获取询问", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {HistoryEvent.getMatch();}
        });
        getHistory.addActionListener(e-> HistoryDialog.getInstance().setVisible(true));
        orderNextParagraph.addActionListener(ArticleTreeListener.getInstance());
        save.addActionListener(ArticleTreeListener.getInstance());
        nonOrder.addActionListener(MixListener.getInstance());
        createCodeTable.addActionListener(new BuildChooseFile());

        getArticleByClipboard.addActionListener(e-> {
            Article.getArticleSingleton(1,"剪贴板载文", Clipboard.get());
            Replay.start();
        });

        help.addActionListener(e-> HelpDialog.showHelp());
        everydayMatchRanking.addActionListener(e-> TljMatchRank.getInstance().setVisible(true));
        moreRanking.addActionListener(e-> {
            Desktop desktop = Desktop.getDesktop();
            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    URI uri = new URI("https://tlj.wiki/home/allUser");
                    desktop.browse(uri);
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        lookPlay.addActionListener(e->{
            if(SwingSingleton.TypingText().getText().length()==0)return;
            TypingText().setEditable(false); // 设置不可打字状态
            Typing.delaySendResultSign = true;
        });

        update.addActionListener(e->{
            try{
                if(FinalConfig.VERSION.equals(OnlineConfig.NEW_VERSION)){
                    JOptionPane.showMessageDialog(MainFra.getInstance(),"已最新");
                    return;
                }
                if(SystemUtil.isWindows())
                    Runtime.getRuntime().exec("更新.exe");
                else
                    Runtime.getRuntime().exec("java -jar update.jar");
                System.exit(0);
            }catch(Exception ignored){}
        });
        watchMode.addActionListener(e-> SwitchWatchPlay.start());
        listenMode.addActionListener(e-> SwitchListenPlay.start());
        followMode.addActionListener(e-> SwitchFollowPlay.start());
        soundRecordPlay.addActionListener(e-> ListenPlay.start());

    }
}
