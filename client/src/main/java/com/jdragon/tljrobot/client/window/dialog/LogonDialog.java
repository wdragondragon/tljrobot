package com.jdragon.tljrobot.client.window.dialog;

import com.jdragon.tljrobot.client.component.JMenuComponent;
import com.jdragon.tljrobot.client.component.SwingSingleton;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.client.event.online.LoginEvent;
import com.jdragon.tljrobot.client.event.online.LogoutEvent;
import com.jdragon.tljrobot.client.event.online.RegEvent;
import com.jdragon.tljrobot.client.utils.core.Layout;
import com.jdragon.tljrobot.client.window.MainFra;

import javax.swing.*;
import java.awt.*;

/**
 * Create by Jdragon on 2020.01.14
 */
public class LogonDialog {
    private LogonDialog(){}
    private static MainFra mainFra = MainFra.getInstance();
    private static JDialog logonDialog;
    private static JTextField username;
    private static JPasswordField password;
    private static JButton confirm;
    private static JButton reset;
    private static JButton reg;
    private static JButton forget;
    private static JButton exit;
    private static JToggleButton runLoginButton;
    private static JPanel p = new JPanel();
    public static JDialog getInstance() {
        if (logonDialog == null) init();
        else logonDialog.setBounds(mainFra.getX()+mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4,255,255);
        return logonDialog;
    }
    private static void init(){
        logonDialog = new JDialog(mainFra, "登录",
                Dialog.ModalityType.DOCUMENT_MODAL);
        logonDialog.setTitle("登录");
        logonDialog.setBounds(mainFra.getX()+mainFra.getWidth()/4,mainFra.getY()+mainFra.getHeight()/4,255,255);
        logonDialog.add(p);
        p.setLayout(null);

        username = new JTextField("账号");
        password = new JPasswordField("密码");
        confirm = new JButton("登录");
        reset = new JButton("清空输入");
        reg = new JButton("注册");
        forget = new JButton("忘记密码");
        runLoginButton = new JToggleButton("自动登录");

        runLoginButton.setSelected(LocalConfig.runLogin);

        Layout.addOnBounds(p,username,20,20,190,30);
        Layout.addOnBounds(p, password,20, 70, 190, 30);
        Layout.addOnBounds(p, confirm, 20, 110, 90, 30);
        Layout.addOnBounds(p, reset, 120, 110, 90, 30);
        Layout.addOnBounds(p, reg, 20, 150, 90, 30);
        Layout.addOnBounds(p, forget,120 , 150, 90, 30);
        Layout.addOnBounds(p,runLoginButton,20,190,190,20);
        username.setText(LocalConfig.username);
        password.setText(LocalConfig.password);

        confirm.addActionListener(e-> doLogin());
        reset.addActionListener(e->{
            username.setText("");
            password.setText("");
        });
        runLoginButton.addChangeListener(e->LocalConfig.runLogin=runLoginButton.isSelected());
        reg.addActionListener(e->doReg());
    }
    public static void doReg(){
        JOptionPane.showMessageDialog(logonDialog,RegEvent.start(username.getText(), new String(password.getPassword())));
    }
    public static void doLogin(){
        getInstance();
        if(UserState.loginState){
            if(LogoutEvent.start()) {
                username.setEditable(true);
                password.setEditable(true);
                confirm.setText("登录");
                reg.setEnabled(true);
                reset.setEnabled(true);
                JMenuComponent.getInstance().getLogin().setText("登录");
                JOptionPane.showMessageDialog(logonDialog,"退出成功");
            }else{
                JOptionPane.showMessageDialog(logonDialog,"退出失败");
            }
        }
        else {
            String loginResult = LoginEvent.start(username.getText(), new String(password.getPassword()));
            if (loginResult.equals("登录成功")) {
                username.setEditable(false);
                password.setEditable(false);
                confirm.setText("退出登录");
                logonDialog.dispose();
                SwingSingleton.WatchingText().setText("登录成功" + "\n" + "欢迎：" + username.getText() + "\n长流交流群:974172771");
                JMenuComponent.getInstance().getLogin().setText(username.getText());
                reg.setEnabled(false);
                reset.setEnabled(false);
                if (LocalConfig.runLogin) {
                    LocalConfig.username = username.getText();
                    LocalConfig.password = new String(password.getPassword());
                }
            }else{
                JOptionPane.showMessageDialog(logonDialog,loginResult);
            }
        }
    }
}
