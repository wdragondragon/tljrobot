package com.jdragon.tljrobot.client.handle.theme;

import javax.swing.*;

/**
 * <p></p>
 * <p>create time: 2022/1/17 1:18 </p>
 *
 * @author : Jdragon
 */
public class LittleLuckTheme extends WindowThemeHandler {

    LittleLuckTheme() {
        super("LittleLuck蓝白", "freeseawind.lf.LittleLuckLookAndFeel");
    }

    @Override
    public void handle() throws Exception {
        UIManager.setLookAndFeel(getClassName());
    }
}
