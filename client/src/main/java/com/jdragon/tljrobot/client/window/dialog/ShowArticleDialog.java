package com.jdragon.tljrobot.client.window.dialog;

import com.jdragon.tljrobot.client.window.MainFra;

import javax.swing.*;
import java.awt.*;

/**
 * Create by Jdragon on 2020.01.20
 */
public class ShowArticleDialog{
    private ShowArticleDialog(){}
    private static MainFra mainFra = MainFra.getInstance();
    public static JDialog getInstance(String content) {
        if (showArticleDialog == null) {
            init();
        }
        setArticleText(content);
        showArticleDialog.setLocation(mainFra.getX()+mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4);
        return showArticleDialog;
    }
    private static JDialog showArticleDialog;
    private static JTextArea articleText = new JTextArea();
    private static JScrollPane articleTextJS = new JScrollPane(articleText);
    private static void init(){
        showArticleDialog = new JDialog(mainFra, "详情",
                Dialog.ModalityType.DOCUMENT_MODAL);
        articleText.setLineWrap(true);
        showArticleDialog.add(articleTextJS);
        showArticleDialog.setBounds(mainFra.getX()+mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4,550,550);
    }
    private static void setArticleText(String content){
        articleText.setText(content);
    }
}
