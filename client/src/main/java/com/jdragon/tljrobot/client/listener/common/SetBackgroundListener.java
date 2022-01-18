package com.jdragon.tljrobot.client.listener.common;

import com.jdragon.tljrobot.client.handle.document.DocumentStyleHandler;
import com.jdragon.tljrobot.client.window.MainFra;
import com.jdragon.tljrobot.client.window.dialog.SetDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.jdragon.tljrobot.client.component.SwingSingleton.typingText;
import static com.jdragon.tljrobot.client.component.SwingSingleton.watchingText;
import static com.jdragon.tljrobot.client.config.LocalConfig.*;
public class SetBackgroundListener implements ActionListener {
	Dialog win = SetDialog.getInstance();
	Color tempColor;

	private final DocumentStyleHandler documentStyleHandler = DocumentStyleHandler.INSTANCE;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
			case "打字框背景颜色":
				tempColor = JColorChooser.showDialog(win, "调色板", Color.white);
				if (tempColor != null) {
					typingText().setBackground(tempColor);
					typingBackgroundColor = tempColor;
				}
				break;
			case "文本框背景颜色":
				tempColor = JColorChooser.showDialog(win, "调色板", Color.white);
				if (tempColor != null) {
					watchingText().setBackground(tempColor);
					watchingBackgroundColor = tempColor;
				}
				break;
			case "打对字颜色":
				tempColor = JColorChooser.showDialog(win, "调色板", Color.gray);
				if (tempColor != null) {
					rightColor = tempColor;
					documentStyleHandler.defineStyle("黑", fontSize, false, false, false, Color.BLACK, family, rightColor);
				}
				break;
			case "打错字颜色":
				tempColor = JColorChooser.showDialog(win, "调色板", Color.red);
				if (tempColor != null) {
					mistakeColor = tempColor;
				}
				documentStyleHandler.defineStyle("红", fontSize, false, false, false, Color.BLACK, family, mistakeColor);
				break;
			case "整体界面颜色":
				tempColor = JColorChooser.showDialog(win, "调色板", Color.red);
				if (tempColor != null) {
					UIBackgroundColor = tempColor;
					MainFra.getInstance().getContentPane().setBackground(UIBackgroundColor);
				}
				break;
			case "全码词颜色":
				tempColor = JColorChooser.showDialog(win, "调色板", new Color(128, 138, 135));
				if (tempColor != null) {
					fourCodeColor = tempColor;
					documentStyleHandler.defineStyle("绿粗", fontSize, true, false, false, fourCodeColor, family, mistakeColor);//GRAY
					documentStyleHandler.defineStyle("绿", fontSize, false, false, false, fourCodeColor, family, mistakeColor);//GRAY
					documentStyleHandler.defineStyle("绿斜", fontSize, false, true, false, fourCodeColor, family, mistakeColor);//GRAY
					documentStyleHandler.defineStyle("绿粗斜", fontSize, true, true, false, fourCodeColor, family, mistakeColor);//GRAY
				}
				break;
			case "二码词颜色":
				tempColor = JColorChooser.showDialog(win, "调色板", Color.ORANGE);
				if (tempColor != null) {
					twoCodeColor = tempColor;
					documentStyleHandler.defineStyle("粉粗", fontSize, true, false, false, twoCodeColor, family, mistakeColor);//GRAY
					documentStyleHandler.defineStyle("粉", fontSize, false, false, false, twoCodeColor, family, mistakeColor);///GRAY
					documentStyleHandler.defineStyle("粉斜", fontSize, true, false, false, twoCodeColor, family, mistakeColor);///GRAY
					documentStyleHandler.defineStyle("粉粗斜", fontSize, true, false, false, twoCodeColor, family, mistakeColor);///GRAY
				}

				break;
			case "三码词颜色":
				tempColor = JColorChooser.showDialog(win, "调色板", Color.BLUE);
				if (tempColor != null) {
					threeCodeColor = tempColor;
					documentStyleHandler.defineStyle("蓝粗", fontSize, true, false, false, threeCodeColor, family, mistakeColor);//GRAY
					documentStyleHandler.defineStyle("蓝", fontSize, false, false, false, threeCodeColor, family, mistakeColor);///GRAY
					documentStyleHandler.defineStyle("蓝斜", fontSize, true, false, false, threeCodeColor, family, mistakeColor);///GRAY
					documentStyleHandler.defineStyle("蓝粗斜", fontSize, true, false, false, threeCodeColor, family, mistakeColor);///GRAY//GRAY
				}
				break;
		}
	}
}