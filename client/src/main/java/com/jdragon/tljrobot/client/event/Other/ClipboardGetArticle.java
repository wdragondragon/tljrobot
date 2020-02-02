package com.jdragon.tljrobot.client.event.Other;

import com.jdragon.tljrobot.client.entry.Article;
import com.jdragon.tljrobot.client.event.FArea.Replay;
import com.jdragon.tljrobot.client.utils.common.Clipboard;

/**
 * Create by Jdragon on 2020.02.02
 */
public class ClipboardGetArticle {
    public static void start(){
        Article.getArticleSingleton(1,"剪贴板载文", Clipboard.get());
        Replay.start();
    }
}
