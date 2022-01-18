package com.jdragon.tljrobot.client.config;

import com.jdragon.tljrobot.client.constant.Constant;
import com.jdragon.tljrobot.client.handle.document.DocumentStyleHandler;

import java.awt.*;

import static com.jdragon.tljrobot.client.config.LocalConfig.*;

public class JTextPaneFontConfig {

    private static final DocumentStyleHandler documentStyleHandler = DocumentStyleHandler.INSTANCE;

    public static void start() {
//        JTextPaneFont.creat(typeDocName);
        if (LocalConfig.typingPattern.equals(Constant.FOLLOW_PLAY_PATTERN)) {
            documentStyleHandler.defineStyle("黑", fontSize,
                    false, false, false, Color.BLACK, family, rightColor);
            documentStyleHandler.defineStyle("红", fontSize,
                    false, false, false, Color.BLACK, family, mistakeColor);
        } else {
            documentStyleHandler.defineStyle("黑", fontSize,
                    false, false, false, Color.BLACK, family, watchingBackgroundColor);
            documentStyleHandler.defineStyle("红", fontSize,
                    false, false, false, Color.BLACK, family, watchingBackgroundColor);
        }
        documentStyleHandler.defineStyle("灰", fontSize,
                false, false, false, Color.BLACK, family, mistakeColor);// GRAY

        documentStyleHandler.defineStyle("蓝粗", fontSize,
                true, false, false, threeCodeColor, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("蓝", fontSize,
                false, false, false, threeCodeColor, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("蓝斜", fontSize,
                false, true, false, threeCodeColor, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("蓝粗斜", fontSize,
                true, true, false, threeCodeColor, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("粉粗", fontSize,
                true, false, false, twoCodeColor, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("粉", fontSize,
                false, false, false, twoCodeColor, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("粉斜", fontSize,
                false, true, false, twoCodeColor, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("粉粗斜", fontSize,
                true, true, false, twoCodeColor, family, mistakeColor);// GRAY

        documentStyleHandler.defineStyle("绿粗", fontSize,
                true, false, false, fourCodeColor, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("绿", fontSize,
                false, false, false, fourCodeColor, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("绿斜", fontSize,
                false, true, false, fourCodeColor, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("绿粗斜", fontSize,
                true, true, false, fourCodeColor, family, mistakeColor);// GRAY

        documentStyleHandler.defineStyle("对", LocalConfig.fontSize, false, false, false, Color.BLACK, LocalConfig.family,
                LocalConfig.rightColor);
        documentStyleHandler.defineStyle("错原", LocalConfig.fontSize, false, false, false, Color.BLACK, LocalConfig.family,
                LocalConfig.mistakeColor);

        documentStyleHandler.defineStyle("多", fontSize,
                true, false, false, Color.pink, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("少", fontSize,
                false, false, false, Color.gray, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("错", (int) (fontSize * 0.6),
                true, false, true, Color.blue, family, mistakeColor);// GRAY
        documentStyleHandler.defineStyle("忽略", fontSize,
                false, false, false, Color.CYAN, family, rightColor);// GRAY
    }
}
