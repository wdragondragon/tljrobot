package com.jdragon.tljrobot.client;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Create by Jdragon on 2020.02.13
 */
public class Client {
    static String ip = "127.0.0.1";
//    static  String ip = "192.168.162.128";
    static Socket socket;
    public static void main(String[] args) {
//        socket = new Socket(ip,1230);
        for (int i = 0; i < 5; i++) {
            new GetImgThread().start();
//        DataInputStream in;
//        DataOutputStream out;

//        in = new DataInputStream(socket.getInputStream());
//        out = new DataOutputStream(socket.getOutputStream());

        }
    }
    static class GetImgThread extends Thread{
        @Override
        @SneakyThrows
        public void run(){
            ShowFra showFra = new ShowFra();
            socket = new Socket(ip,1230);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            while (true) {
                try {
                    PackBean packBean = (PackBean)in.readObject();
                    byte[] bytes = (byte[])packBean.getData();
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                    BufferedImage image = ImageIO.read(byteArrayInputStream);
                    showFra.jLabel.setIcon(new ImageIcon(image));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static class ShowFra extends JFrame{
        JLabel jLabel = new JLabel();
        ShowFra(){
//            init();
            this.setTitle("远程监控--IP:"  + "--主题:" );
            this.setSize(400, 400);
            //this.setUndecorated(true);  //全屏显示，测试时最好注释掉
            //this.setAlwaysOnTop(true);  //显示窗口始终在最前面
            this.add(jLabel);
            this.setLocationRelativeTo(null);
//            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setVisible(true);
            this.validate();
        }
    }
}
