package com.jdragon.tljrobot.client.handle.theme;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * <p></p>
 * <p>create time: 2022/1/17 0:46 </p>
 *
 * @author : Jdragon
 */

@Getter
@Slf4j
public abstract class WindowThemeHandler {

    protected final String themeName;

    protected final String className;

    WindowThemeHandler(String themeName, String className) {
        this.themeName = themeName;
        this.className = className;
    }

    private boolean valid() {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            log.error("主题包未找到，可能缺失类[{}]", className, e);
        } catch (Exception e) {
            log.error("主题包[{}]加载失败", themeName, e);
        }
        return false;
    }

    public void activation() {
        if (!valid()) {
            throw new RuntimeException("主题包未找到");
        }
        try {
            handle();
        } catch (Exception e) {
            log.error("主题包[{}]加载失败", themeName, e);
            throw new RuntimeException("主题包加载失败", e);
        }
    }

    protected abstract void handle() throws Exception;
}
