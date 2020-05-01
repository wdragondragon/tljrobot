package com.jdragon.tljrobot.client.test.swing;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.04.20 23:41
 * @Description:
 */
public class testBug {
    static JTextPane jTextPane;
    public static void main(String[] args){
        jTextPane = new JTextPane();
        (jTextPane.getDocument()).addDocumentListener(new TypingListener());
        jTextPane.addKeyListener(new TypingListener());
        JFrame f  = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(jTextPane);

        f.pack();
        f.setVisible(true);


    }
    public static class TypingListener implements DocumentListener, KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println("keyTyped");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("keyPressed");
        }

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("keyReleased");
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            changedUpdate(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changedUpdate(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            System.out.println("changedUpdate");
            jTextPane.setEnabled(false);
        }
    }
}
