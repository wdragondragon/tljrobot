package com.jdragon.tljrobot.client.handle.theme;

import javax.swing.*;

/**
 * <p></p>
 * <p>create time: 2022/1/17 1:22 </p>
 *
 * @author : Jdragon
 */
public class NimbusTheme extends WindowThemeHandler {
    NimbusTheme() {
        super("Nimbus灰白", "javax.swing.plaf.nimbus.NimbusLookAndFeel");
    }

    @Override
    public void handle() throws Exception {
        UIManager.setLookAndFeel(getClassName());
    }
}
