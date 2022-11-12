package com.jdragon.tljrobot.client.listener.common;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.entry.TypingState;
import com.jdragon.tljrobot.client.event.FArea.ReplayEvent;
import com.jdragon.tljrobot.client.event.FArea.ShareArticleEvent;
import com.jdragon.tljrobot.client.utils.common.Clipboard;
import com.jdragon.tljrobot.client.utils.common.Code;
import com.jdragon.tljrobot.client.utils.core.Layout;
import com.jdragon.tljrobot.client.window.dialog.SendArticleDialog;
import com.jdragon.tljrobot.tljutils.ArticleUtil;
import com.jdragon.tljrobot.tljutils.CodePointString;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

/**
 * Create by Jdragon on 2020.01.25
 */
public class ArticleTreeListener implements TreeSelectionListener, ActionListener {
    private static ArticleTreeListener articleTreeListener = new ArticleTreeListener();
    private ArticleTreeListener(){}
    public static ArticleTreeListener getInstance(){return articleTreeListener;}
    public static int fontnum = 0, fontweizhi = 0,startParagraph = 1;
    byte[] s;
    public static String all, wen;
    public static long length = 0;
    File open;
    RandomAccessFile in = null;

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        try {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) SendArticleDialog.tree
                    .getLastSelectedPathComponent();
            if (node.isLeaf()) {
                Article article = Article.getArticleSingleton();
                fontweizhi = 0;
                article.setTitle(node.toString());
                if("随机一文".equals(article.getTitle())){
                    all = ArticleUtil.getRandomContent2();
                }else if("剪贴板".equals(article.getTitle())){
                    all = Clipboard.get();
                }else{
                    if ("跟打进度".equals(article.getTitle().substring(0, 4))) {
                        readjindu();
                    }
                    open = new File("文章//" + node.getParent(), article.getTitle());
                    in = new RandomAccessFile(open, "r");
                    length = in.length();
                    s = new byte[(int) length];
                    in.readFully(s);
                    all = new String(s);
                }
                if(LocalConfig.clearSpace) {
                    all = ArticleUtil.clearSpace(all);
                }
                if(LocalConfig.replace) {
                    all = ArticleUtil.replace(all);
                }
                length = all.length();
                SendArticleDialog.getInstance().setTitle("文章总长度:"+length);
                getNumber();
                showContent();
            }
        } catch (Exception ignored) {
        }
    }
    public static void getNumber() {
        try {
            fontnum = Integer.parseInt(SendArticleDialog.number.getText());
            startParagraph = Integer.parseInt(SendArticleDialog.paragraph.getText());
        } catch (Exception e) {
//			JOptionPane.showMessageDialog(new JTextArea(), "字数框输入数字");
        }
    }
    public static void showContent() {
        if (fontnum > all.length()) {
            wen = all.substring(fontweizhi);
        } else {
            wen = all.substring(fontweizhi, fontweizhi + fontnum);
        }
        if(wen.length()>500) {
            SendArticleDialog.wenben.setText(wen.substring(0,500));
        } else {
            SendArticleDialog.wenben.setText(wen);
        }
        // fontweizhi += fontnum;
        SwingSingleton.sendArticleLabel().setText(fontweizhi
                + "/"
                + all.length()
                + ":"
                + String.format("%.2f",
                (double) fontweizhi * 100 / all.length()) + "%");
    }
    void readjindu() throws IOException {
        try {
            open = new File("文章//文章类", Article.getArticleSingleton().getTitle());
            Reader read = new FileReader(open);
            BufferedReader br = new BufferedReader(read);
            Article.getArticleSingleton().setTitle(br.readLine());
            fontweizhi = Integer.parseInt(br.readLine());
            br.close();
            read.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void sendOrder(){
        Article article = Article.getArticleSingleton();
        showContent();
        article.setArticle(wen);//设置跟打内容
        if (article.getArticle() == null
                || "".equals(article.getArticle())) {
            return;
        }
        ReplayEvent.start();
        TypingState.sendArticle = 1; // 顺序发文标志
        article.setParagraph(startParagraph=Integer.parseInt(SendArticleDialog.paragraph.getText()));//设置段号
        fontweizhi += fontnum;
        SwingSingleton.sendArticleLabel().setVisible(true);
        SendArticleDialog.getInstance().setVisible(false);
        if (!LocalConfig.lurk) {
            ShareArticleEvent.start();
        }
    }
    public void nextOrder(){
        try {
            if (fontweizhi >= all.length()) {
                JOptionPane.showMessageDialog(new JTextArea(), "发文结束");
                SwingSingleton.sendArticleLabel().setVisible(false);
                TypingState.sendArticle = 0;
                return;
            }
            if (fontweizhi + fontnum >= all.length()) {
                wen = all.substring(fontweizhi);
                fontweizhi = all.length();
            } else {
                wen = all.substring(fontweizhi, fontweizhi + fontnum);
                fontweizhi += fontnum;
            }
            Article article = Article.getArticleSingleton();
            article.setArticle(wen);
            article.addParagraph();// 发文增段

            SwingSingleton.sendArticleLabel().setText(fontweizhi
                    + "/"
                    + all.length()
                    + ":"
                    + String.format("%.2f", (double) fontweizhi * 100
                    / all.length()) + "%");
            ShareArticleEvent.start();
            ReplayEvent.start();
        } catch (Exception ex) {
            System.out.println("发文处失败");
        }
    }
    public void save(){
        try {
            Article article = Article.getArticleSingleton();
            if(article.getTitle().equals("随机一文")){
                JOptionPane.showMessageDialog(new JTextArea(), "随机一文暂时不支持保存进度");
                return;
            }
            String jindufile = "跟打进度" + article.getTitle();
            open = new File("文章//文章类", jindufile);
            FileOutputStream testfile = new FileOutputStream(open);
            testfile.write("".getBytes());
            byte[] baocun = (article.getTitle() + "\r\n" + (fontweizhi - fontnum)).getBytes();
            testfile.write(baocun);
            testfile.close();
            JOptionPane.showMessageDialog(new JTextArea(), "已保存当前跟打进度");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JTextArea(), "保存进度失败");
        }
    }
    public static List<CodePointString> chouqulist = new ArrayList<>();
    public static List<CodePointString> chouqubufenlist = new ArrayList<>();
    public void chouqu(String model){
        Article article = Article.getArticleSingleton();
        getNumber();
        article.setArticle(randomCommon(all, fontnum));
        if (article.getArticle() == null) {
            return;
        }
        if (model.equals("抽取模式发文")) {
            SendArticleDialog.getInstance().setVisible(false);
            article.setParagraph(startParagraph=Integer.parseInt(SendArticleDialog.paragraph.getText()));
            TypingState.sendArticle = Constant.SEND_EXTRACT;
        }else if(model.equals("下一段")){
            article.addParagraph(); // 发文增段
        }
        ShareArticleEvent.start();
        ReplayEvent.start();
    }
    public static int wordNum;
    public void ciKu(){
        StringBuilder temp = new StringBuilder();
        chouqulist.clear();
        chouqubufenlist.clear();
        Article article = Article.getArticleSingleton();
        article.setTitle("词库练习");
        Code code = Code.getInstance(LocalConfig.codeTable);
        HashMap<CodePointString,Integer> selectTable;
        try {
            if(Objects.requireNonNull(SendArticleDialog.weizhi.getSelectedItem()).toString().equals("首选")){
                selectTable = code.firstTable;
            }else if(SendArticleDialog.weizhi.getSelectedItem().toString().equals("次选")){
                selectTable = code.otherTable;
            }else {
                selectTable = code.allTable;
            }
            for(Map.Entry<CodePointString,Integer> entry:selectTable.entrySet()){
                int wordLength = entry.getKey().length();
                int codeLength = entry.getValue();
                int wordLength1 = Integer.parseInt(String.valueOf(SendArticleDialog.cichang1.getValue()));
                int wordLength2 = Integer.parseInt(String.valueOf(SendArticleDialog.cichang2.getValue()));
                int codeLength1 = Integer.parseInt(String.valueOf(SendArticleDialog.machang1.getValue()));
                int codeLength2 = Integer.parseInt(String.valueOf(SendArticleDialog.machang2.getValue()));
                if((wordLength1==0&&wordLength2==0)||(wordLength >= wordLength1 && wordLength <= wordLength2)){
                    if((codeLength1==0&&codeLength2==0)||(codeLength >= codeLength1 && codeLength <= codeLength2)) {
                        chouqulist.add(entry.getKey());
                    }
                }
            }
            Collections.shuffle(chouqulist);
            // System.out.println(chouqulist.size()+" "+y);
            wordNum = Integer.parseInt(String.valueOf(SendArticleDialog.cishu.getValue()));
            if(wordNum==0) {
                wordNum = chouqulist.size();
            }
            for (int i = 0; i < (Math.min(chouqulist.size(), wordNum)); i++) {
                temp.append(chouqulist.get(i));
                chouqubufenlist.add(chouqulist.get(i));
            }
            article.setArticle(temp.toString());
            article.setParagraph(startParagraph=Integer.parseInt(SendArticleDialog.paragraph.getText()));
            ReplayEvent.start();
            TypingState.sendArticle = Constant.SEND_WORDS;
            SendArticleDialog.getInstance().setVisible(false);
        } catch (Exception ex) {
             ex.printStackTrace();
        }
    }
    public void ciKuNext(){
        if(TypingState.sendArticle == Constant.SEND_WORDS){
            chouqubufenlist.clear();
            Collections.shuffle(chouqulist);
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < (Math.min(chouqulist.size(), wordNum)); i++) {
                str.append(chouqulist.get(i));
                chouqubufenlist.add(chouqulist.get(i));
            }
            Article.getArticleSingleton().setTitle("词库练习");
            Article.getArticleSingleton().setArticle(str.toString());
            Article.getArticleSingleton().addParagraph();
            ShareArticleEvent.start();
            ReplayEvent.start();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) SendArticleDialog.tree
                .getLastSelectedPathComponent();
        if(node!=null&&node.isLeaf()){
            Article.getArticleSingleton().setTitle(node.toString().replaceAll("跟打进度",""));
        }
        switch (e.getActionCommand()) {

            case "下一段":
                if (TypingState.sendArticle == Constant.SEND_ORDER) {
                    nextOrder();
                } else if (TypingState.sendArticle == Constant.SEND_EXTRACT) {
                    chouqu(e.getActionCommand());
                } else if (TypingState.sendArticle == Constant.SEND_WORDS) {
                    ciKuNext();
                }
                break;
            case "顺序模式发文":
                sendOrder();
                break;
            case "抽取模式发文":
                chouqu(e.getActionCommand());
                break;
            case "词库练习":
                ciKu();
                break;
            case "保存进度":
                save();
                break;
            case "发送全文":
                sendAll();
                break;
            default:break;
        }
        Layout.resetBounds();
        SwingSingleton.typingText().requestFocusInWindow();

    }
    public void sendAll() {
        Article article = Article.getArticleSingleton();
        article.setArticle(all);//设置跟打内容
        if (article.getArticle() == null
                || "".equals(article.getArticle())) {
            return;
        }
        ReplayEvent.start();
        TypingState.sendArticle = 1; // 顺序发文标志
        article.setParagraph(startParagraph=Integer.parseInt(SendArticleDialog.paragraph.getText()));//设置段号
        fontweizhi += fontnum;
        SwingSingleton.sendArticleLabel().setVisible(true);
        SendArticleDialog.getInstance().setVisible(false);
        ShareArticleEvent.start();
    }
    public static String randomCommon(String wen, int n) {
        if (wen == null) {
            return null;
        }
        int min = 0;
        int max = wen.length();
        char[] c = wen.toCharArray();
        StringBuilder resultstr = new StringBuilder();
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        for (int value : result) {
            resultstr.append(c[value]);
        }
        return resultstr.toString();
    }
}
