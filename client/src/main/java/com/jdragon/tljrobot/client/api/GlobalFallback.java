package com.jdragon.tljrobot.client.api;

import com.jdragon.tljrobot.tljutils.zFeign.HttpException;
import com.jdragon.tljrobot.tljutils.zFeign.ZFeignFallback;

import javax.swing.*;

/**
 * <p></p>
 * <p>create time: 2021/12/29 1:02 </p>
 *
 * @author : Jdragon
 */

public class GlobalFallback implements ZFeignFallback {
    @Override
    public Object fallback(HttpException httpException) {
        JOptionPane.showMessageDialog(null, httpException.getMessage());
        return null;
    }
}
