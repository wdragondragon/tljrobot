package com.jdragon.tljrobot.client.test.swing;

import com.jdragon.tljrobot.client.constant.Constant;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTextPaneUI;
import javax.swing.text.*;
import java.awt.*;
import java.util.HashMap;


public class JpaneText {
    public static HashMap<String,MutableAttributeSet>  attributeSetHashMap = new HashMap<>();
    @SneakyThrows
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextPane pane = new JTextPane();
        pane.setEditorKit(new CustomEditorKit());
        pane.setText("字体趑的三非的三地方字体趑的三非的三地方字体趑的三非的三地方字体趑的三非的三地方");

        StyledDocument doc = (StyledDocument) pane.getDocument();

        MutableAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrs, 20); // 大小
        StyleConstants.setBold(attrs, true); // 粗体
        StyleConstants.setItalic(attrs, true); // 斜体
        StyleConstants.setUnderline(attrs,true); // 下划线
        StyleConstants.setForeground(attrs, Color.red); // 颜色
        StyleConstants.setFontFamily(attrs, "微软雅黑");// 字体
        attrs.addAttribute("Underline-Color", Color.red);
        attrs.addAttribute("Number","1");

        MutableAttributeSet paragraph = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(paragraph, Constant.LINE_SPACING); //此处设定行间距

        MutableAttributeSet attrs1 = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrs1, 20); // 大小
        StyleConstants.setBold(attrs1, false); // 粗体
        StyleConstants.setItalic(attrs1, false); // 斜体
        StyleConstants.setUnderline(attrs1,false); // 下划线
        StyleConstants.setForeground(attrs1, Color.red); // 颜色
        StyleConstants.setFontFamily(attrs1, "微软雅黑");// 字体
        attrs.addAttribute("Underline-Color", Color.black);

        attributeSetHashMap.put("all",attrs);
        attributeSetHashMap.put("allNo",attrs1);

        pane.setParagraphAttributes(paragraph, false);
        doc.setCharacterAttributes(2, doc.getLength() - 6, attributeSetHashMap.get("all"), false);
        doc.setCharacterAttributes(0,2,attributeSetHashMap.get("allNo"),false);

        System.out.println(doc.getLength());
        doc.insertString(doc.getLength(),"插入",attributeSetHashMap.get("allNo"));
        System.out.println(doc.getLength());
        JScrollPane sp = new JScrollPane(pane);
        frame.setContentPane(sp);
        frame.setPreferredSize(new Dimension(400, 300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class CustomEditorKit extends StyledEditorKit {

    public ViewFactory getViewFactory() {
        return new CustomUI();
    }
}

class CustomUI extends BasicTextPaneUI {
    @Override
    public View create(Element elem) {
        View result = null;
        String kind = elem.getName();
        if (kind != null) {
            if (kind.equals(AbstractDocument.ContentElementName)) {
                result = new MyLabelView(elem);
            } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                result = new ParagraphView(elem);
            } else if (kind.equals(AbstractDocument.SectionElementName)) {
                result = new BoxView(elem, View.Y_AXIS);
            } else if (kind.equals(StyleConstants.ComponentElementName)) {
                result = new ComponentView(elem);
            } else if (kind.equals(StyleConstants.IconElementName)) {
                result = new IconView(elem);
            } else {
                result = new LabelView(elem);
            }
        } else {
            result = super.create(elem);
        }
        return result;
    }
}

class MyLabelView extends LabelView {

    public MyLabelView(Element arg0) {
        super(arg0);
    }

    public void paint(Graphics g, Shape a) {
        super.paint(g, a);
        Color c = (Color) getElement().getAttributes().getAttribute(
                "Underline-Color");
        String s = (String)getElement().getAttributes().getAttribute("Number");
        if (c != null) {
            int y = a.getBounds().y + (int) getGlyphPainter().getAscent(this)+4;
            int x1 = a.getBounds().x;
            int x2 = a.getBounds().width + x1;

            g.setColor(c);
            g.drawLine(x1, y, x2, y);
        }
        if(s != null){
            int y = a.getBounds().y + (int) getGlyphPainter().getAscent(this)+15;
            int x1 = a.getBounds().x;
            int x2 = a.getBounds().width + x1;

            g.setColor(Color.BLACK);
            g.setFont(new Font("宋体",0,10));
            g.drawString(s,(x1+x2)/2,y);
        }
    }
}