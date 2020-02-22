package com.jdragon.tljrobot.client.test.swing;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class JTextPaneTest {
    private static void appendToPane(JTextPane tp, String msg, Color f, Color b) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, f);
        aset = sc.addAttribute(aset, StyleConstants.Background, b);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment,
                StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    public static void main(String... args) {
        JTextPane tPane = new JTextPane();
        JFrame f  = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        appendToPane(tPane, "this is a test.\n", Color.RED, Color.WHITE);
        appendToPane(tPane, "this is a test \n", Color.PINK,Color.BLUE);
        appendToPane(tPane, "test", Color.GRAY, Color.BLACK);
        appendToPane(tPane, "test", Color.RED, Color.BLUE);
        appendToPane(tPane, "test", Color.RED, Color.YELLOW);

        f.getContentPane().add(tPane);

        f.pack();
        f.setVisible(true);
    }
}