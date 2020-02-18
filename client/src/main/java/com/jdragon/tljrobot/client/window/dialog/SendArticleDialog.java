package com.jdragon.tljrobot.client.window.dialog;

import com.jdragon.tljrobot.client.listener.common.ArticleTreeListener;
import com.jdragon.tljrobot.client.listener.common.MixListener;
import com.jdragon.tljrobot.client.window.MainFra;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * Create by Jdragon on 2020.01.25
 */
public class SendArticleDialog {
    private SendArticleDialog(){}
    private static MainFra mainFra = MainFra.getInstance();
    private static ArticleTreeListener articleTreeListener = ArticleTreeListener.getInstance();;
    private static JDialog sendArticleDialog = null;
    public static JTree tree;
    static JScrollPane tree1;
    static DefaultMutableTreeNode root;
    static DefaultMutableTreeNode danzilei, wenzhanglei, yingwenlei;
    static JButton send, next, mix, chouqu, cikuchouqu, English,sendAll;
    public static JTextField number;
    public static JTextArea wenben;
    public static JScrollPane wenben1;
    public static JSpinner machang1,machang2,cishu,cichang1,cichang2;
    public static JComboBox<String> caozuo,weizhi;
    public static JSpinner spinnerSpeed,spinnerKey,spinnerKeyLength;
    public static JPanel p = new JPanel();
    public static JDialog getInstance() {
        if (sendArticleDialog == null) {
            init();
        }
        sendArticleDialog.setBounds(mainFra.getX()+mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4,605, 430);
        return sendArticleDialog;
    }
    private static void init(){
        sendArticleDialog = new JDialog(mainFra, "发文",
                Dialog.ModalityType.DOCUMENT_MODAL);
        p.setLayout(null);
        sendArticleDialog.add(p);
        articleList();//文章列表
        addNumber();//字数区
        addinArea();//文本区
        addOrder();//顺序发文按钮及功能
        addMix();//全局乱序按钮及功能
        addNoOrder();//抽取发文按钮及功能
        addSendAll();//发送全文按钮
        addcikuchouqu();//词库联系按钮及功能
        automaticDisruption();//自动下一段条件选择及功能
    }
    private static void addNoOrder() {
        chouqu = new JButton("抽取模式发文");
        chouqu.setBounds(245, 270, 100, 30);
        p.add(chouqu);
        chouqu.addActionListener(articleTreeListener);
    }

    private static void addMix() {
        mix = new JButton("全局乱序");
        mix.setBounds(170, 270, 70, 30);
        p.add(mix);
        mix.addActionListener(MixListener.getInstance());
    }
    private static void addOrder() {
        send = new JButton("顺序模式发文");
        send.setBounds(65, 270, 100, 30);
        p.add(send);
        send.addActionListener(articleTreeListener);
    }
    private static void addSendAll(){
        sendAll = new JButton("发送全文");
        sendAll.setBounds(425, 270, 90, 30);
        p.add(sendAll);
        sendAll.addActionListener(articleTreeListener);
    }
    private static void addcikuchouqu() {
        cikuchouqu = new JButton("词库练习");

        cikuchouqu.setBounds(350, 270, 70, 30);

        JLabel lable1 = new JLabel("码长");
        JLabel lable2 = new JLabel("组数");
        JLabel lable3 = new JLabel("词长");
        JLabel lable4 = new JLabel("次首选 ");


        machang1 = new JSpinner();
        machang1.setModel(new SpinnerNumberModel(0, 0, 6, 1));
        machang2 = new JSpinner();
        machang2.setModel(new SpinnerNumberModel(0, 0, 6, 1));
        cishu = new JSpinner();
        cishu.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        cichang1 = new JSpinner();
        cichang1.setModel(new SpinnerNumberModel(0, 0, 50, 1));
        cichang2 = new JSpinner();
        cichang2.setModel(new SpinnerNumberModel(0, 0, 50, 1));
        weizhi = new JComboBox<>() ;
        weizhi.addItem("全部");
        weizhi.addItem("首选");
        weizhi.addItem("次选");

        lable1.setBounds(5, 310, 30, 30);
        machang1.setBounds(lable1.getX()+lable1.getWidth()+5,lable1.getY(),50,30);
        machang2.setBounds(machang1.getX()+machang1.getWidth()+3,lable1.getY(),50,30);

        lable2.setBounds(machang2.getX()+machang2.getWidth()+5, lable1.getY(), 30, 30);
        cishu.setBounds(lable2.getX()+lable2.getWidth()+3,lable2.getY(),50,30);

        lable3.setBounds(cishu.getX()+cishu.getWidth()+5, lable1.getY(), 30, 30);
        cichang1.setBounds(lable3.getX()+lable3.getWidth()+3,lable3.getY(),50,30);
        cichang2.setBounds(cichang1.getX()+cichang1.getWidth()+3,lable3.getY(),50,30);

        lable4.setBounds(cichang2.getX()+cichang2.getWidth()+5, lable1.getY(), 50, 30);
        weizhi.setBounds(lable4.getX()+lable4.getWidth()+3,lable4.getY(),70,30);


        cikuchouqu.addActionListener(articleTreeListener);
        p.add(cikuchouqu);
        p.add(lable1);
        p.add(lable2);
        p.add(lable3);
        p.add(lable4);
        p.add(machang1);
        p.add(machang2);
        p.add(weizhi);
        p.add(cichang1);
        p.add(cichang2);
        p.add(cishu);
    }
    private static void automaticDisruption() {
        JLabel lable1 = new JLabel("速度≥");
        JLabel lable2 = new JLabel("击键≥");
        JLabel lable3 = new JLabel("键准≥");
        JLabel lable4 = new JLabel("未达标时");
        lable1.setBounds(5, 350, 40, 30);
        spinnerSpeed = new JSpinner();
        spinnerSpeed.setModel(new SpinnerNumberModel(0, 0, 999, 0.1));
        spinnerSpeed.setBounds(lable1.getX()+lable1.getWidth()+10, lable1.getY(), 50, 30);

        lable2.setBounds(spinnerSpeed.getX()+spinnerSpeed.getWidth()+10,  lable1.getY(), 40, 30);
        spinnerKey = new JSpinner();
        spinnerKey.setModel(new SpinnerNumberModel(0, 0, 30, 0.1));
        spinnerKey.setBounds(lable2.getX()+lable2.getWidth()+10,  lable1.getY(), 50, 30);

        lable3.setBounds(spinnerKey.getX()+spinnerKey.getWidth()+10,  lable1.getY(), 40, 30);
        spinnerKeyLength = new JSpinner();
        spinnerKeyLength.setModel(new SpinnerNumberModel(0, 0, 100, 0.01));
        spinnerKeyLength.setBounds(lable3.getX()+lable3.getWidth()+10,  lable1.getY(), 50, 30);


        lable4.setBounds(spinnerKeyLength.getX()+spinnerKeyLength.getWidth()+10, lable1.getY(),50,30);
        caozuo = new JComboBox<>();
        caozuo.addItem("不操作");
        caozuo.addItem("乱序");
        caozuo.addItem("重打");
        caozuo.setBounds(lable4.getX()+lable4.getWidth()+10,  lable1.getY(), 100, 30);



        p.add(spinnerSpeed);
        p.add(spinnerKey);
        p.add(spinnerKeyLength);
        p.add(caozuo);
        p.add(lable1);
        p.add(lable2);
        p.add(lable3);
        p.add(lable4);
    }
    private static void addNumber() {
        number = new JTextField("10");
        number.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyReleased(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
//						keyTyped(e);
                int keyChar = e.getKeyChar();
                if (keyChar >= KeyEvent.VK_0
                        && keyChar <= KeyEvent.VK_9|| keyChar == '\b') {
                    ArticleTreeListener.getNumber();
                    ArticleTreeListener.showContent();
                }
                else {
                    e.consume();
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                keyReleased(e);
            }
        });
        number.setBounds(5, 270, 50, 30);
        p.add(number);
    }
    private static void addinArea() {
        wenben = new JTextArea();
        wenben1 = new JScrollPane(wenben);
        wenben.setLineWrap(true);
        wenben1.setBounds(191, 0, 400, 270);
        p.add(wenben1);
    }
    private static void articleList() {
        root = new DefaultMutableTreeNode("练习");
        danzilei = new DefaultMutableTreeNode("单字类");
        wenzhanglei = new DefaultMutableTreeNode("文章类");
        yingwenlei = new DefaultMutableTreeNode("英打类");
        File danziDir = new File("文章//单字类"), wenzhangDir = new File("文章//文章类"), yingwenDir = new File(
                "文章//英打类");

        File[] danziFile = danziDir.listFiles(), wenzhangFile = wenzhangDir
                .listFiles(), yingwenFile = yingwenDir.listFiles();
        String[] danziFileName = danziDir.list(), wenzhangFileName = wenzhangDir
                .list(), yingwenFileName = yingwenDir.list();
        root.add(new DefaultMutableTreeNode("剪贴板"));
        root.add(new DefaultMutableTreeNode("随机一文"));
        for (int i = 0; i < danziFileName.length; i++) {
            if (danziFile[i].isFile()) {
                danzilei.add(new DefaultMutableTreeNode(danziFileName[i]));
            }
        }
        for (int i = 0; i < wenzhangFileName.length; i++) {
            if (wenzhangFile[i].isFile()) {
                wenzhanglei
                        .add(new DefaultMutableTreeNode(wenzhangFileName[i]));
            }
        }
        for (int i = 0; i < yingwenFileName.length; i++) {
            if (yingwenFile[i].isFile()) {
                yingwenlei.add(new DefaultMutableTreeNode(yingwenFileName[i]));
            }
        }
        root.add(danzilei);
        root.add(wenzhanglei);
        root.add(yingwenlei);

        tree = new JTree(root);
        tree1 = new JScrollPane(tree);


        tree.addTreeSelectionListener(articleTreeListener);
        tree1.setBounds(0, 0, 190, 270);
        p.add(tree1);
    }
}
