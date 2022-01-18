package com.jdragon.tljrobot.client.handle.document;

import lombok.Getter;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p>create time: 2022/1/18 21:57 </p>
 *
 * @author : Jdragon
 */
public class DocumentStyle {

    private static final Map<String, MutableAttributeSet> styleMap = new HashMap<>();

    private static final List<String> colorBackgroundList = Arrays.asList("黑", "红", "对", "错原", "忽略");

    @Getter
    private final String styleName;

    @Getter
    private final MutableAttributeSet mutableAttributeSet;

    DocumentStyle(String styleName, MutableAttributeSet mutableAttributeSet) {
        this.styleName = styleName;
        this.mutableAttributeSet = mutableAttributeSet;
    }

    protected static MutableAttributeSet get(String styleName) {
        return styleMap.get(styleName);
    }

    protected static void defineStyle(String styleName, int size, boolean bold, boolean italic, boolean underline,
                                                   Color color, String fontFamily, Color backColor) {
        MutableAttributeSet styledDocument = styleMap.get(styleName);
        if (styledDocument == null) {
            styledDocument = new SimpleAttributeSet();
            styleMap.put(styleName, styledDocument);
        }
        StyleConstants.setFontSize(styledDocument, size); // 大小
        StyleConstants.setBold(styledDocument, bold); // 粗体
        StyleConstants.setItalic(styledDocument, italic); // 斜体
        StyleConstants.setUnderline(styledDocument, underline); // 下划线
        StyleConstants.setForeground(styledDocument, color); // 颜色
        StyleConstants.setFontFamily(styledDocument, fontFamily);// 字体
        if (colorBackgroundList.contains(styleName)) {
            StyleConstants.setBackground(styledDocument, backColor);
        }
    }
}
