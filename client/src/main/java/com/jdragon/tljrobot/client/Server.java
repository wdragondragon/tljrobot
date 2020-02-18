package com.jdragon.tljrobot.client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Create by Jdragon on 2020.02.13
 */
public class Server {
    static int port = 1230;
    static Dimension screen;
    static Rectangle rect ;
    static Robot robot;
    public static void main(String[] args) throws IOException, AWTException {
        ServerSocket server = new ServerSocket(port);
        screen = Toolkit.getDefaultToolkit().getScreenSize();  //获取主屏幕的大小
        rect = new Rectangle(screen);                          //构造屏幕大小的矩形
        robot = new Robot();
        while (true){
            Socket socket = server.accept();
            System.out.println(socket.getInetAddress().getHostAddress()+"进入");
            new sendImgThread(socket).start();
        }
    }
    public static class sendImgThread extends Thread{
        Socket socket;
//        DataInputStream in = null;
//        DataOutputStream out = null;
        sendImgThread(Socket socket){
            this.socket = socket;
        }
        public void run(){
            try {
//                in = new DataInputStream(socket.getInputStream());
//                out = new DataOutputStream(socket.getOutputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                while (true){
                    try {
                        BufferedImage img = robot.createScreenCapture(rect);
                        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                        ImageIO.write(img, "jpg", byteOut);
                        PackBean packBean = new PackBean();
                        packBean.setData(byteOut.toByteArray());
                        packBean.setPacketType("image");
                        out.writeObject(packBean);
                    }catch (Exception e){}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
