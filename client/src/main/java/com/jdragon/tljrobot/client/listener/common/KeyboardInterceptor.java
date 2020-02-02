package com.jdragon.tljrobot.client.listener.common;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Create by Jdragon on 2020.01.20
 */
public class KeyboardInterceptor implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
        keyReleased(e);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int keyChar = e.getKeyChar();
        if (keyChar < KeyEvent.VK_0
                || keyChar > KeyEvent.VK_9&& keyChar != '\b') {
            e.consume();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        keyReleased(e);
    }
}
