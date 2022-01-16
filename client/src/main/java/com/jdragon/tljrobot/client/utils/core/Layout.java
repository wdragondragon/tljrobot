package com.jdragon.tljrobot.client.utils.core;

import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.window.MainFra;

import java.awt.*;

import static com.jdragon.tljrobot.client.component.SwingSingleton.*;

public class Layout {

    //默认X间隔
    public static int DEFAULT_X_SPACE = 5;

    //默认Y间隔
    public static int DEFAULT_Y_SPACE = 5;

    /**
     * 指定location来添加组件在某个容器上
     */
    public static void addOnLocation(Container win, Component component, int x, int y) {
        win.add(component);
        component.setLocation(x, y);
    }

    /**
     * 指定bounds来添加组件在某个容器上
     */
    public static void addOnBounds(Container win, Component component, int x, int y, int width, int height) {
        win.add(component);
        component.setBounds(x, y, width, height);
    }

    /**
     * 在原组件的基础上增加x,y
     */
    public static void addLocation(int x, int y, Component... components) {
        for (Component component : components) {
            component.setLocation(component.getX() + x, component.getY() + y);
        }
    }

    /**
     * 在原组件的基础上增加width,height
     */
    public static void addSize(int width, int height, Component... components) {
        for (Component component : components) {
            component.setSize(component.getWidth() + width, component.getHeight() + height);
        }
    }

    /**
     * 在原组件的基础上增加x,y,width,height
     */
    public static void addBounds(int x, int y, int width, int height, Component... components) {
        for (Component component : components) {
            component.setBounds(component.getX() + x, component.getY() + y, component.getWidth() + width, component.getHeight() + height);
        }
    }

    /**
     * 根据某个组件获取指定间隔的x轴位置
     */
    public static int xSpace(Component component, int spacing) {
        return component.getX() + component.getWidth() + spacing;
    }

    /**
     * 根据某个组件获取指定间隔的y轴位置
     */
    public static int ySpace(Component component, int spacing) {
        return component.getY() + component.getHeight() + spacing;
    }

    /**
     * 以begin为起始位置，添加以默认间隔的横向组件组
     *
     * @param win        需要往哪个组件添加
     * @param begin      起始位置
     * @param components 组件组
     */
    public static void addXSpaceDefault(Container win, Point begin, Component... components) {
        deleteComponent(win, components);
        Component temp = components[0];
        temp.setLocation(begin);
        win.add(temp);
        for (int i = 1; i < components.length; i++) {
            addOnLocation(win, components[i], xSpace(temp, DEFAULT_X_SPACE), temp.getY());
            temp = components[i];
        }
    }

    public static void addXSpaceDefault(Container win, Component component, Component... components) {
        Point begin = new Point(xSpace(component, DEFAULT_X_SPACE), component.getY());
        addXSpaceDefault(win, begin, components);
    }

    /**
     * 以begin为起始位置，添加以默认间隔的竖向组件组
     *
     * @param win        需要往哪个组件添加
     * @param begin      起始位置
     * @param components 组件组
     */
    public static void addYSpaceDefault(Container win, Point begin, Component... components) {
        deleteComponent(win, components);
        Component temp = components[0];
        temp.setLocation(begin);
        win.add(temp);
        for (int i = 1; i < components.length; i++) {
            addOnLocation(win, components[i], temp.getX(), ySpace(temp, DEFAULT_Y_SPACE));
            temp = components[i];
        }
    }

    public static void addYSpaceDefault(Container win, Component component, Component... components) {
        Point begin = new Point(component.getX(), ySpace(component, DEFAULT_Y_SPACE));
        addYSpaceDefault(win, begin, components);
    }

    public static void deleteComponent(Container win, Component... components) {
        for (Component component : components) {
            if (win.isAncestorOf(component)) {
                win.remove(component);
            }
        }
    }

    public static void resetBounds() {
        MainFra mainFra = MainFra.getInstance();
        int systemAddWidth = LocalConfig.undecorated ? 0 : 15;
        int systemAddHeight = LocalConfig.undecorated ? 0 : 40;

        int topButtonWidth = (mainFra.getWidth() - systemAddWidth - (jMenu().getX() + jMenu().getWidth() + 40)) / 4;
        speedButton().setBounds(speedButton().getX(), speedButton().getY(), topButtonWidth, SwingSingleton.speedButton().getHeight());
        keySpeedButton().setBounds(xSpace(speedButton(), 10), speedButton().getY(), topButtonWidth, speedButton().getHeight());
        keyLengthButton().setBounds(xSpace(keySpeedButton(), 10), speedButton().getY(), topButtonWidth, speedButton().getHeight());
        theoreticalCodeLengthButton().setBounds(xSpace(keyLengthButton(), 10), speedButton().getY(), topButtonWidth, SwingSingleton.speedButton().getHeight());
        int baseBottomLabelWidth;
        if (sendArticleLabel().isVisible()) {
            baseBottomLabelWidth = (mainFra.getWidth() - systemAddWidth - 20) / 10;
            sendArticleLabel().setSize(2 * baseBottomLabelWidth, sendArticleLabel().getHeight());
        } else {
            baseBottomLabelWidth = (mainFra.getWidth() - systemAddWidth - 15) / 8;
        }


        closeButton().setBounds(mainFra.getWidth() - systemAddWidth - 20, 0, 20, 10);
        maxButton().setBounds(mainFra.getWidth() - systemAddWidth - 42, 0, 20, 10);
        minButton().setBounds(mainFra.getWidth() - systemAddWidth - 63, 0, 20, 10);
        sizeButton().setBounds(mainFra.getWidth() - systemAddWidth - 10, mainFra.getHeight() - 10, 10, 10);
        typingAndWatching().setBounds(0,
                ySpace(SwingSingleton.speedButton(), 5), mainFra.getWidth() - systemAddWidth, mainFra.getHeight() - 95 - systemAddHeight);
        typingProgress().setBounds(0,
                ySpace(typingAndWatching(), 0), mainFra.getWidth(), 10);
        qQNameLabel().setBounds(qQNameLabel().getX(), ySpace(typingAndWatching(), 10), baseBottomLabelWidth * 3 / 2, qQNameLabel().getHeight());
        numberLabel().setBounds(xSpace(qQNameLabel(), 5), qQNameLabel().getY(), baseBottomLabelWidth * 3 / 2, qQNameLabel().getHeight());
        numberRecordLabel().setBounds(xSpace(numberLabel(), 5), qQNameLabel().getY(), 3 * baseBottomLabelWidth, qQNameLabel().getHeight());
        tipsLabel().setBounds(xSpace(numberRecordLabel(), 5), qQNameLabel().getY(), 2 * baseBottomLabelWidth, qQNameLabel().getHeight());
        sendArticleLabel().setLocation(xSpace(tipsLabel(), 5), qQNameLabel().getY());
        LocalConfig.windowWidth = mainFra.getWidth();
        LocalConfig.windowHeight = mainFra.getHeight();
        typingAndWatching().validate();
    }
}
