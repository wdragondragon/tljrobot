package com.jdragon.tljrobot.client.handle;

import org.jvnet.substance.SubstanceLookAndFeel;

import javax.swing.*;

/**
 * <p></p>
 * <p>create time: 2022/1/17 1:20 </p>
 *
 * @author : Jdragon
 */
public class SubstanceTheme extends WindowThemeHandle{
    SubstanceTheme() {
        super("Substance黑白", "org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel");
    }

    @Override
    public void handle() throws Exception {
        UIManager.setLookAndFeel(getClassName());
        SubstanceLookAndFeel.setCurrentTheme("org.jvnet.substance.theme.SubstanceAquaTheme");
    }
}
