package com.jdragon.tljrobot.client.handle;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;

/**
 * <p></p>
 * <p>create time: 2022/1/17 0:55 </p>
 *
 * @author : Jdragon
 */

public class BeautyEyeTheme extends WindowThemeHandle {

    BeautyEyeTheme() {
        super("长流默认", "org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper");
    }

    @Override
    public void handle() throws Exception {
        BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle
                .generalNoTranslucencyShadow;
        BeautyEyeLNFHelper.launchBeautyEyeLNF();
        UIManager.put("RootPane.setupButtonVisible", false);
    }

}
