package com.jdragon.tljrobot.client.handle.theme;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p></p>
 * <p>create time: 2022/1/17 1:04 </p>
 *
 * @author : Jdragon
 */
public class ThemeManager {

    private final static Map<String, WindowThemeHandler> themeHandleMap = new LinkedHashMap<>();

    public final static ThemeManager INSTANCE = new ThemeManager();

    private final static WindowThemeHandler defaultTheme = new BeautyEyeTheme();

    static {
        ThemeManager.INSTANCE.register(defaultTheme);
        ThemeManager.INSTANCE.register(new SystemTheme());
        ThemeManager.INSTANCE.register(new LittleLuckTheme());
        ThemeManager.INSTANCE.register(new SubstanceTheme());
        ThemeManager.INSTANCE.register(new NimbusTheme());
    }

    private ThemeManager() {
    }

    public void register(WindowThemeHandler windowThemeHandler) {
        String themeName = windowThemeHandler.getThemeName();
        themeHandleMap.put(themeName, windowThemeHandler);
    }

    public static WindowThemeHandler getTheme(String themeName) {
        WindowThemeHandler theme = themeHandleMap.get(themeName);
        if (theme == null) {
            theme = defaultTheme;
        }
        return theme;
    }

    public static Set<String> getAllTheme(){
        return themeHandleMap.keySet();
    }

}
