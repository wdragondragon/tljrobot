package com.jdragon.tljrobot.client.utils.common;

import com.jdragon.tljrobot.client.config.FinalConfig;
import com.jdragon.tljrobot.client.config.LocalConfig;
import com.jdragon.tljrobot.client.entry.UserState;
import com.jdragon.tljrobot.tljutils.string.Comparison;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

import static com.jdragon.tljrobot.client.entry.TypingState.*;

/**
 * Create by Jdragon on 2020.02.11
 */
public class DrawUnLookPlayResult {
    public static void main(String[] args) throws IOException {
        List<HashMap<String, Integer>> hashMapList =
                Comparison.getComparisonListenResult("为什么呢。为什么这样呢。我知道，三。。三",
                        "为你么。为什么这样呢。我知道，三。。三是的", BetterTypingSingleton.getInstance().getSymbolCode());
        drawUnFollowPlayResultImg("title", hashMapList, "听打");
    }

    public static int fontSize = 30;
    public static int style = Font.PLAIN;

    enum lookPlayColor {
        right(0, Color.black, new Font("微软雅黑", style, fontSize)),
        miss(1, Color.white, new Font("微软雅黑", style, fontSize)),
        more(2, Color.pink, new Font("微软雅黑", style, fontSize)),
        mistake(3, Color.blue, new Font("微软雅黑", style, fontSize / 2)),
        mistakeRight(4, Color.red, new Font("微软雅黑", style, fontSize)),
        ignore(5, Color.CYAN, new Font("微软雅黑", style, fontSize));
        private Color color;
        private int type;
        private Font font;

        lookPlayColor(int type, Color color, Font font) {
            this.type = type;
            this.color = color;
            this.font = font;
        }

        public static Color getColor(int type) {
            for (lookPlayColor typeColor : lookPlayColor.values()) {
                if (typeColor.type == type) {
                    return typeColor.color;
                }
            }
            return null;
        }

        public static Font getFont(int type) {
            for (lookPlayColor typeColor : lookPlayColor.values()) {
                if (typeColor.type == type)
                    return typeColor.font;
            }
            return null;
        }
    }

    public static String drawUnFollowPlayResultImg(String title, java.util.List<HashMap<String, Integer>> strList, String model) {
        class DrawSub {
            int x, y;
            String word;
            Color color;
            Font font;

            DrawSub(int x, int y, String word, Color color, Font font) {
                this.x = x;
                this.y = y;
                this.word = word;
                this.color = color;
                this.font = font;
            }
        }
        List<DrawSub> drawSubList = new ArrayList<>();
        int imageWidth = 1000;
        int imageHeight = 65;

        int imageWidthTemp = 10;
        int length = 0, more = 0, miss = 0, mistake = 0, ignore = 0, correct = 0;
        for (HashMap<String, Integer> strHashMap : strList) {
            for (Map.Entry<String, Integer> entry : strHashMap.entrySet()) {
                int type = entry.getValue();
                String word = entry.getKey();
                if ((type == 4 && imageWidthTemp + fontSize * 3 / 2 > imageWidth) || imageWidthTemp + 20 > imageWidth) {
                    imageWidthTemp = 10;
                    imageHeight += fontSize + 10;
                }
                DrawSub drawSub = new DrawSub(imageWidthTemp, imageHeight - 20, word, lookPlayColor.getColor(type), lookPlayColor.getFont(type));
                drawSubList.add(drawSub);
                imageWidthTemp += type == lookPlayColor.mistake.type ? fontSize / 2 + 3 : fontSize + 3;
                length++;
                switch (type) {
                    case 0:
                        correct++;
                        break;
                    case 1:
                        miss++;
                        break;
                    case 2:
                        more++;
                        break;
                    case 3:
                        mistake++;
                        break;
                    case 5:
                        ignore++;
                        break;
                    default:
                        break;
                }
            }
        }
        length -= (ignore + mistake + more);
        //偏移
        int moveX = 10;
        int moveYBottom = 30;
        int moveYTop = 110;


        BufferedImage image = new BufferedImage(imageWidth + 2 * moveX, imageHeight + moveYTop + moveYBottom, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

//        graphics.setColor(new Color(238, 238, 238));
        //画第一层背景
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, imageWidth + 2 * moveX, imageHeight + moveYBottom + moveYTop);
        //画标题
        graphics.setFont(new Font("宋体", Font.BOLD, 30));
        graphics.setColor(Color.white);
        int strWidth = graphics.getFontMetrics().stringWidth(title);
        graphics.drawString(title, (imageWidth + 2 * moveX - strWidth) / 2, 40);
        //画颜色说明
        graphics.setFont(new Font("宋体", Font.BOLD, 15));

        double speed = getSpeed();
        double noMisSpeed = getSpeedNoMistake();
        double keySpeed = getKeySpeed();
        double keyLength = getKeyLength();
        String noMisSpeedStr = mistake == 0 ? "" : ("/" + String.format("%.2f", noMisSpeed));
        String playResultStr2 = "速度" + String.format("%.2f", speed) + (LocalConfig.errorPunishment ? noMisSpeedStr : "") +
                " 击键" + String.format("%.2f", keySpeed) +
                " 码长" + String.format("%.2f", keyLength) +
                " 回改" + deleteTextNumber +
                " 退格" + deleteNumber +
                " 空格" + space +
                " 重打" + getRetry();
        graphics.drawString(playResultStr2, moveX, moveYTop - 50);

        String help = "听打".equals(model) ?
                "黑色：对、白色：少、粉色：多、红色：错、蓝色：错原字、青色：忽略" : "黑色：对、白色：少、粉色：多、红色：错、蓝色：错原字";
        graphics.drawString(help, moveX, moveYTop - 30);
        //画时间和详情
        int mistakeAll = mistake + miss;
        if (!LocalConfig.morePunishment) {
            mistakeAll += more;
        }
        String playResultStr = "文章总长" + length + " 错" + mistakeAll + "处" +
                " 分别错:" + mistake + " 少:" + miss + " 多:" + more +
                " 正确率：" + String.format("%.2f", ((double) correct) * 100 / length) + "%";
        playResultStr += "听打".equals(model) ? " 忽略符号:" + ignore : "";
        graphics.drawString(playResultStr, moveX, moveYTop - 10);
        if (LocalConfig.useTime) {
            String useTime = model + "用时 " + timer.formatSeconds();
            strWidth = graphics.getFontMetrics().stringWidth(useTime);
            graphics.drawString(useTime, imageWidth + moveX - strWidth, moveYTop - graphics.getFontMetrics().getHeight() - 10);
        }

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String modelAndTimeStr = model + "详细 " + time;
        strWidth = graphics.getFontMetrics().stringWidth(modelAndTimeStr);
        graphics.drawString(modelAndTimeStr, imageWidth + moveX - strWidth, moveYTop - 10);

        //画脚注
        graphics.setColor(Color.GRAY);
        graphics.setFont(new Font("宋体", Font.BOLD, 15));
        String foot = "该图由长流跟打器" + FinalConfig.VERSION + " " + model + "模式制作";
        if (UserState.loginState) {
            foot = "该图由[" + UserState.loginUserName + "][" + model + "]制作";
        }
        strWidth = graphics.getFontMetrics().stringWidth(foot);
        graphics.drawString(foot, imageWidth + moveX - strWidth, imageHeight + moveYTop + 20);
        //画第二层背景
        graphics.setColor(Color.GRAY);
        graphics.fillRect(moveX, moveYTop, imageWidth, imageHeight);
        //画第二层背景内容
        for (DrawSub drawSub : drawSubList) {
            String word = drawSub.word;
            graphics.setColor(drawSub.color);
            graphics.setFont(drawSub.font);
            graphics.drawString(word, drawSub.x + moveX, drawSub.y + moveYTop);
        }

        //设置参数
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        Stroke s = new BasicStroke(imageWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setStroke(s);
        //画图
        String path = time.replaceAll(":", "-") + ".png";
        graphics.drawImage(image.getScaledInstance(imageWidth + 2 * moveX, imageHeight + moveYTop + moveYBottom, Image.SCALE_SMOOTH), 0, 0, null);
        //判断系统
        String localPath = "imageResult" + File.separator + title + File.separator;
        File file = new File(localPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        localPath = localPath + path;
        try {
            ImageIO.write(image, "png", new File(localPath));
            Clipboard.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}

