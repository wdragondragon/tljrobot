package com.jdragon.tljrobot.client.handle.document;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;

/**
 * <p></p>
 * <p>create time: 2022/1/18 21:55 </p>
 *
 * @author : Jdragon
 */
@Slf4j
public class DocumentStyleHandler {

    public static DocumentStyleHandler INSTANCE = new DocumentStyleHandler();

    private DocumentStyleHandler(){};

    public void defineStyle(String styleName, int size, boolean bold, boolean italic, boolean underline,
                            Color color, String fontFamily, Color backColor) {
        DocumentStyle.defineStyle(styleName, size, bold, italic, underline, color, fontFamily, backColor);
    }

    public void insertDoc(String content, String styleName) {
        try {
            StyledDocument doc = (StyledDocument) SwingSingleton.watchingText().getDocument();
            MutableAttributeSet styledDoc = DocumentStyle.get(styleName);
            doc.insertString(doc.getLength(), content, styledDoc);
        } catch (Exception e) {
            log.error("插入Doc失败", e);
        }
    }

    public void insertDoc(String content, String styleName, MutableAttributeSet mutableAttributeSet) {
        try {
            MutableAttributeSet styledDoc = new SimpleAttributeSet(DocumentStyle.get(styleName));
            styledDoc.addAttributes(mutableAttributeSet);
            StyledDocument doc = (StyledDocument) SwingSingleton.watchingText().getDocument();
            doc.insertString(doc.getLength(), content, styledDoc);
        } catch (Exception e) {
            log.error("插入Doc失败", e);
        }
    }

    public void updateDocStyle(int index, String styleName, MutableAttributeSet... mutableAttributeSet) {
        MutableAttributeSet style = DocumentStyle.get(styleName);
        if (style != null) {
            updateDocStyle(index, 1, styleName, mutableAttributeSet);
        }
    }

    public void updateDocStyle(int index, int length, String styleName, MutableAttributeSet... mutableAttributeSet) {
        MutableAttributeSet style = DocumentStyle.get(styleName);
        if (style != null) {
            if (mutableAttributeSet != null) {
                for (MutableAttributeSet set : mutableAttributeSet) {
                    style.addAttributes(set);
                }
            }
            updateDocStyle(index, length, style);
        }
    }

    public void updateDocStyle(int index, int length, MutableAttributeSet style) {
        StyledDocument doc = (StyledDocument) SwingSingleton.watchingText().getDocument();
        doc.setCharacterAttributes(index, length, style, true);
    }
}
