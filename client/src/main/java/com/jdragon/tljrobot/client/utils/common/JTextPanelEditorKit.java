package com.jdragon.tljrobot.client.utils.common;


import com.jdragon.tljrobot.client.config.LocalConfig;

import javax.swing.plaf.basic.BasicTextPaneUI;
import javax.swing.text.*;
import java.awt.*;


public class JTextPanelEditorKit extends StyledEditorKit{
    public ViewFactory getViewFactory() {
        return new CustomUI();
    }
    static class CustomUI extends BasicTextPaneUI {
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
    static class MyLabelView extends LabelView {

        public MyLabelView(Element arg0) {
            super(arg0);
        }

        public void paint(Graphics g, Shape a) {
            super.paint(g, a);
            Color c = (Color) getElement().getAttributes().getAttribute(
                    "Underline-Color");
            Boolean underlineOpen = (Boolean)getElement().getAttributes().getAttribute("UnderlineOpen");
            String s = (String) getElement().getAttributes().getAttribute("Number");
            Color foreground = (Color)getElement().getAttributes().getAttribute("foreground");
            if (underlineOpen != null&&underlineOpen) {
                int y = a.getBounds().y + (int) getGlyphPainter().getAscent(this) + LocalConfig.fontSize/4;
                int x1 = a.getBounds().x + LocalConfig.fontSize/10;
                int x2 = a.getBounds().width + x1 - LocalConfig.fontSize/4;
                if(c==null) {
                    g.setColor(foreground);
                }else{
                    g.setColor(c);
                }
                g.drawLine(x1, y, x2, y);
            }
            if (s != null) {
                int y = a.getBounds().y + (int) getGlyphPainter().getAscent(this) + LocalConfig.fontSize*3/4;
                int x1 = a.getBounds().x;
                int x2 = a.getBounds().width + x1;

                g.setColor(Color.BLACK);
                g.setFont(new Font(LocalConfig.family, Font.BOLD,  LocalConfig.fontSize/2));
                g.drawString(s, (x1 + x2) / 2, y);
            }
        }
    }
}