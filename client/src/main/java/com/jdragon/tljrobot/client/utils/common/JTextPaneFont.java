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
    static public void createStyle(String styleName, int size, int bold, int italic, int underline,
                                   Color color, String fontFamily, Color backColor) {
        MutableAttributeSet styledDocument = styleSets.get(styleName);;
        if(styledDocument!=null) {
            styleSets.remove(styleName);
        }else {
            styleSets.put(styleName,styledDocument = new SimpleAttributeSet());
        }
        StyleConstants.setFontSize(styledDocument, size); // 大小
        StyleConstants.setBold(styledDocument, bold == 1); // 粗体
        StyleConstants.setItalic(styledDocument, italic == 1); // 斜体
        StyleConstants.setUnderline(styledDocument, underline == 1); // 下划线
        StyleConstants.setForeground(styledDocument, color); // 颜色
        StyleConstants.setFontFamily(styledDocument, fontFamily);// 字体
        List<String> colorBackgroundList = Arrays.asList("黑","红","对","错原","忽略");
        if (colorBackgroundList.contains(styleName))
            StyleConstants.setBackground(styledDocument, backColor);
    }
}