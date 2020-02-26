package com.jdragon.tljrobot.client.utils.common;

import com.jdragon.tljrobot.client.component.SwingSingleton;

import javax.swing.text.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JTextPaneFont {
    public static HashMap<String,MutableAttributeSet> styleSets = new HashMap<>();
    static public void insertDoc(String content,String styleName) {
        try {
            StyledDocument doc = (StyledDocument) SwingSingleton.watchingText().getDocument();
            MutableAttributeSet styledDoc = styleSets.get(styleName);;
            doc.insertString(doc.getLength(), content,styledDoc);
        } catch (BadLocationException e) {
            System.err.println("BadLocationException: " + e);
        }
    }
    static public void insertDoc(String content,String styleName,MutableAttributeSet mutableAttributeSet){
        try {
            MutableAttributeSet styledDoc = new SimpleAttributeSet(styleSets.get(styleName));
            styledDoc.addAttributes(mutableAttributeSet);
            StyledDocument doc = (StyledDocument) SwingSingleton.watchingText().getDocument();
            doc.insertString(doc.getLength(), content,styledDoc);
        } catch (BadLocationException e) {
            System.err.println("BadLocationException: " + e);
        }
    }
    static public void updateOneDocStyleByStyleName(int index, String styleName, boolean replace){
        MutableAttributeSet style = styleSets.get(styleName);
        if(style!=null){
            updateDocStyleOnStyleName(index,1,styleName,replace);
        }
    }
    static public void updateDocStyleOnStyleName(int index,int length,String styleName,boolean replace){
        MutableAttributeSet style = styleSets.get(styleName);
        if(style!=null){
            updateDocStyle(index,length,style,replace);
        }
    }
    static public void updateDocStyle(int index,int length,MutableAttributeSet style,boolean replace){
        StyledDocument doc = (StyledDocument) SwingSingleton.watchingText().getDocument();
        doc.setCharacterAttributes(index,length,style,replace);
    }
    static public void createStyle(String styleName, int size, boolean bold, boolean italic, boolean underline,
                                   Color color, String fontFamily, Color backColor) {
        MutableAttributeSet styledDocument = styleSets.get(styleName);;
        if(styledDocument!=null) {
            styleSets.remove(styleName);
        }
        styleSets.put(styleName,styledDocument = new SimpleAttributeSet());
        StyleConstants.setFontSize(styledDocument, size); // 大小
        StyleConstants.setBold(styledDocument, bold); // 粗体
        StyleConstants.setItalic(styledDocument, italic); // 斜体
        StyleConstants.setUnderline(styledDocument, underline); // 下划线
        StyleConstants.setForeground(styledDocument, color); // 颜色
        StyleConstants.setFontFamily(styledDocument, fontFamily);// 字体
//        if(underline) {
//            styledDocument.addAttribute("Underline-Color", color);
//        }
        List<String> colorBackgroundList = Arrays.asList("黑","红","对","错原","忽略");
        if (colorBackgroundList.contains(styleName))
            StyleConstants.setBackground(styledDocument, backColor);
    }
//    static public void createStyle(String styleName, int size, boolean bold, boolean italic, boolean underline,
//                                   Color color, String fontFamily, Color backColor,String number) {
//        createStyle(styleName,size,bold,italic,underline,color,fontFamily,backColor);
//        styleSets.get(styleName).addAttribute("Number",number);
//    }
}