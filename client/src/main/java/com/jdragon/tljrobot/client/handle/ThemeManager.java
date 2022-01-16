package com.jdragon.tljrobot.client.handle;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p></p>
 * <p>create time: 2022/1/17 1:04 </p>
 *
 * @author : Jdragon
 */
public class ThemeManager {

    private final static Map<String, WindowThemeHandle> themeHandleMap = new LinkedHashMap<>();

    public final static ThemeManager INSTANCE = new ThemeManager();

    private final static WindowThemeHandle defaultTheme = new BeautyEyeTheme();

    static {
        ThemeManager.INSTANCE.register(defaultTheme);
        ThemeManager.INSTANCE.register(new SystemTheme());
        ThemeManager.INSTANCE.register(new LittleLuckTheme());
        ThemeManager.INSTANCE.register(new SubstanceTheme());
        ThemeManager.INSTANCE.register(new NimbusTheme());
    }

    private ThemeManager() {
    }

    public void register(WindowThemeHandle windowThemeHandle) {
        String themeName = windowThemeHandle.getThemeName();
        themeHandleMap.put(themeName, windowThemeHandle);
    }

    public static WindowThemeHandle getTheme(String themeName) {
        WindowThemeHandle theme = themeHandleMap.get(themeName);
        if (theme == null) {
            theme = defaultTheme;
        }
        return theme;
    }

    public static Set<String> getAllTheme(){
        return themeHandleMap.keySet();
    }

}
