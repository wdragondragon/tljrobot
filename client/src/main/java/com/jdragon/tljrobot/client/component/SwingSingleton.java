package com.jdragon.tljrobot.client.component;


import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.utils.common.JTextPaneFont;
import com.jdragon.tljrobot.client.window.CirecordFra;
import com.jdragon.tljrobot.client.window.dialog.ShowArticleDialog;

import javax.swing.*;
import java.awt.*;


public class SwingSingleton {
    private static Font nonSizeFont = new Font(LocalConfig.family, Font.PLAIN, 0);
    private static Font normalFont =  new Font(LocalConfig.family, Font.PLAIN, LocalConfig.fontSize);
    private static Font tipsFont = new Font(LocalConfig.family, Font.PLAIN, 12);
    private static JMenuBar jMenu;
    private static JButton speedButton,keySpeedButton,keyLengthButton,theoreticalCodeLength;
    private static JTextArea typingText;
    private static JTextPane watchingText;
    private static JScrollPane watchingJSP, typingJSP;
    private static JSplitPane typingAndWatching;
    private static JProgressBar typingProgress;
    private static JButton closeButton, maxButton, sizeButton, minButton;
    private static JLabel qqNameLabel,numberLabel,numberRecordLabel,sendArticleLabel,tipsLabel;
    public static JLabel numberRecordLabel(){
        if (numberRecordLabel==null){
            numberRecordLabel = new JLabel("总:" + 0 +
                    " 对:" + 0 +
                    " 错:" + 0 +
                    " 今:" + 0);
            numberRecordLabel.setBorder(BorderFactory.createTitledBorder("跟打记录"));
            numberRecordLabel.setFont(tipsFont);
        }
        return numberRecordLabel;
    }
    public static JLabel qQNameLabel(){
        if (qqNameLabel==null){
            qqNameLabel = new JLabel("F5更换跟打群");
            qqNameLabel.setBorder(BorderFactory.createTitledBorder("跟打群"));
            qqNameLabel.setFont(tipsFont);
        }
        return qqNameLabel;
    }
    public static JLabel numberLabel(){
        if (numberLabel==null){
            numberLabel = new JLabel("字数:0/已打:0/错:0");
            numberLabel.setBorder(BorderFactory.createTitledBorder("字数"));
            numberLabel.setFont(tipsFont);
        }
        return numberLabel;
    }
    public static JLabel sendArticleLabel(){
        if (sendArticleLabel ==null){
            sendArticleLabel = new JLabel("发文进度");
            sendArticleLabel.setBorder(BorderFactory.createTitledBorder("发文进度"));
            sendArticleLabel.setFont(tipsFont);
            sendArticleLabel.setVisible(false);
        }
        return sendArticleLabel;
    }
    public static JLabel tipsLabel(){
        if (tipsLabel==null){
            tipsLabel = new JLabel("编码提示");
            tipsLabel.setBorder(BorderFactory.createTitledBorder("编码提示"));
            tipsLabel.setFont(tipsFont);
        }
        return tipsLabel;
    }

    public static JButton closeButton(){
        if (closeButton==null){
            closeButton = new JButton("关");
            closeButton.setFont(nonSizeFont);
        }
        return closeButton;
    }
    public static JButton maxButton(){
        if (maxButton==null){
            maxButton = new JButton("最大化");
            maxButton.setFont(nonSizeFont);
        }
        return maxButton;
    }
    public static JButton sizeButton(){
        if (sizeButton==null){
            sizeButton = new JButton("大小");
            sizeButton.setFont(nonSizeFont);

        }
        return sizeButton;
    }
    public static JButton minButton(){
        if (minButton==null){
            minButton = new JButton("最小化");
            minButton.setFont(nonSizeFont);
        }
        return minButton;
    }
    public static JButton speedButton(){
        if (speedButton==null){
            speedButton = new JButton("速度");
            speedButton.addActionListener(e->new CirecordFra());
        }
        return speedButton;
    }
    public static JButton keySpeedButton(){
        if (keySpeedButton==null) {
            keySpeedButton = new JButton("击键");
        }
        return keySpeedButton;
    }
    public static JButton keyLengthButton(){
        if (keyLengthButton==null) {
            keyLengthButton = new JButton("码长");
        }
        return keyLengthButton;
    }
    public static JButton theoreticalCodeLengthButton(){
        if(theoreticalCodeLength==null){
            theoreticalCodeLength = new JButton("理论码长");
            theoreticalCodeLength.addActionListener(e-> ShowArticleDialog.getInstance(Article.getArticleSingleton().getShortCodeEntity().getArticleCodes()).setVisible(true));
        }
        return theoreticalCodeLength;
    }
    public static JMenuBar jMenu(){
        if (jMenu==null){
            jMenu = new JMenuBar();
            jMenu.setBorder(BorderFactory.createEtchedBorder());
        }
        return jMenu;
    }
    public static JTextPane watchingText(){
        if (watchingText==null) {
            watchingText = new JTextPane(JTextPaneFont.getStyledDocument(LocalConfig.typeDocName));
            watchingText.setText("F5换群，F4载文，F3重打，F2发文，F1发送成绩，默认自动发送成绩。");
            watchingText.setFont(normalFont);
            watchingText.setEditable(false);
            watchingText.setBackground(LocalConfig.watchingBackgroundColor);
        }
        return watchingText;
    }
    public static JTextArea typingText(){
        if (typingText==null) {
            typingText = new JTextArea();
            typingText.setFont(normalFont);
            typingText.setLineWrap(true);
            typingText.setBackground(LocalConfig.typingBackgroundColor);
        }
        return typingText;
    }

    public static JScrollPane watchingJsp(){
        if (watchingJSP==null){
            watchingJSP = new JScrollPane(watchingText());
            watchingJsp().getVerticalScrollBar().addAdjustmentListener(e -> {
                JScrollBar jsb = (JScrollBar)e.getAdjustable();
                if(jsb.getValueIsAdjusting()) {
                    System.out.println("adjusting ...");
                } else {
                    System.out.println(e.getValue());
                }
                System.out.println(jsb.getMaximum()+":"+jsb.getMinimum()+":"+jsb.getValue());
                System.out.println(jsb.getX()+":"+jsb.getY()+":"+jsb.getWidth()+":"+jsb.getHeight());
            });
        }
        return watchingJSP;
    }
    public static JScrollPane typingJsp(){
        if (typingJSP==null) {
            typingJSP = new JScrollPane(typingText());
        }
        return typingJSP;
    }

    public static JSplitPane typingAndWatching(){
        if(typingAndWatching==null) {
            typingAndWatching = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                    true, watchingJsp(), typingJsp());
            typingAndWatching.setDividerSize(5);
        }
        return typingAndWatching;
    }

    public static JProgressBar typingProgress(){
        if (typingProgress==null) {
            typingProgress = new JProgressBar(0,0);
        }
        return typingProgress;
    }
}
