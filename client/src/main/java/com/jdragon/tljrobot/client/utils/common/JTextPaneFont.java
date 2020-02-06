package com.jdragon.tljrobot.client.utils.common;

import javax.swing.text.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JTextPaneFont {
    public static HashMap<String,StyledDocument> styleDocs = new HashMap<>();
    static public void creat(String styleName) {
        if(styleDocs.containsKey(styleName))return;
        StyledDocument styledDoc = new DefaultStyledDocument();
        styleDocs.put(styleName,styledDoc);
    }
    static public StyledDocument getStyledDocument(String styleName){
        return styleDocs.get(styleName);
    }
    static public void insertDoc( String docName, String content,
                                 String currentStyle) {
        try {
            StyledDocument styledDoc = getStyledDocument(docName);
            styledDoc.insertString(styledDoc.getLength(), content,
                    styledDoc.getStyle(currentStyle));
        } catch (BadLocationException e) {
            System.err.println("BadLocationException: " + e);
        }
    }
    static public void createStyle(String style, String docName, int size,
                                   int bold, int italic, int underline, Color color, String fontName,
                                   Color backColor) {
        StyledDocument doc = getStyledDocument(docName);
        Style sys = StyleContext.getDefaultStyleContext().getStyle(
                StyleContext.DEFAULT_STYLE);
        try {
            doc.removeStyle(style);
        } catch (Exception ignored) {
        } // 先删除这种Style,假使他存在

        Style s = doc.addStyle(style, sys); // 加入
        StyleConstants.setFontSize(s, size); // 大小
        StyleConstants.setBold(s, bold == 1); // 粗体
        StyleConstants.setItalic(s, italic == 1); // 斜体
        StyleConstants.setUnderline(s, underline == 1); // 下划线
        StyleConstants.setForeground(s, color); // 颜色
        StyleConstants.setFontFamily(s, fontName);// 字体
        List<String> colorBackgroundList = Arrays.asList("黑","红","对","错原","忽略");
        if (colorBackgroundList.contains(style))
            StyleConstants.setBackground(s, backColor);
    }
}