package com.analysis.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * @author lvshuzheng
 * @className ImgDemo
 * @description
 * @date 2019/7/11
 */
public class GetVerificationCode {

    //学习如何把一个字符串变成图片写到一个文件
    public static void imgDemo1() throws FileNotFoundException, IOException {
        BufferedImage img = new BufferedImage(60, 30, BufferedImage.TYPE_INT_RGB);
        //  表示一个图像，它具有合成整数像素的 8 位 RGB 颜色分量。
        Graphics g = img.getGraphics();
        g.drawString("Hello", 10, 20);
        //使用此图形上下文的当前字体和颜色绘制由指定 string 给定的文本。最左侧字符的基线位于此图形上下文坐标系的 (x, y) 位置处。
        g.dispose();////类似于流中的close()带动flush()---把数据刷到img对象当中
        //释放此图形的上下文以及它使用的所有系统资源。调用 dispose 之后，就不能再使用 Graphics 对象。
        ImageIO.write(img, "JPG", new FileOutputStream("F:/verificationCode/a.jpg"));
        //使用支持给定格式的任意 ImageWriter 将一个图像写入 File。
    }

    //把上面的字符串改成我们平时用的验证码---生成几个随机数字，有背景色和干扰线
    public static void imgDemo2() throws FileNotFoundException, IOException {
        int width = 80;
        int height = 40;
        int lines = 10;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = img.getGraphics();

        //设置背景色
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);//画背景
        //填充指定的矩形。使用图形上下文的当前颜色填充该矩形

        //设置字体
        g.setFont(new Font("宋体", Font.BOLD, 18));

        //随机数字
        Date d = new Date();
        //System.out.println(d.getTime());
        Random r = new Random(d.getTime());
        for (int i = 0; i < 4; i++) {
            int a = r.nextInt(10);//取10以内的整数[0，9]
            int y = 10 + r.nextInt(20); //10~30范围内的一个整数，作为y坐标
            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            g.setColor(c);
            g.drawString("" + a, 5 + i * width / 4, y);
        }
        //干扰线
        for (int i = 0; i < lines; i++) {
            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            g.setColor(c);
            g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
        }

        g.dispose();//类似于流中的close()带动flush()---把数据刷到img对象当中
        ImageIO.write(img, "JPG", new FileOutputStream("F:/verificationCode/b.jpg"));

    }

    //可以旋转和放缩的验证码
    public String imgDemo3(FileOutputStream outputStream) throws FileNotFoundException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int width = 80;
        int height = 40;
        int lines = 3;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) img.getGraphics();

        g2d.setFont(new Font("宋体", Font.BOLD, 20));


        Random r = new Random(System.currentTimeMillis());

        //设置背景色
        g2d.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        g2d.drawRect(0, 0, width, height);//绘制指定矩形的边框。
        g2d.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        g2d.fillRect(0, 0, width, height);//填充指定的矩形。

        for (int i = 0; i < 4; i++) {
            String str = "" + r.nextInt(10);
            stringBuilder.append(str);
            //处理旋转
            AffineTransform Tx = new AffineTransform();
            Tx.rotate(Math.random(), 5 + i * 15, height - 5);
            //用弧度测量的旋转角度,旋转锚点的 X 坐标,旋转锚点的 Y 坐标
            //Tx.scale(0.7+Math.random(), 0.7+Math.random());
            //x坐标方向的缩放倍数，y坐标方向的缩放倍数
            g2d.setTransform(Tx);
            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            g2d.setColor(c);
            g2d.drawString(str, 2 + i * width / 4, height - 13);
        }

        //干扰线
        for (int i = 0; i < lines; i++) {
            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            g2d.setColor(c);
            g2d.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
        }

        g2d.dispose();

        //ImageIO.write(img, "JPG", new FileOutputStream("F:/verificationCode/c.jpg"));
        ImageIO.write(img, "JPG", outputStream);
        return stringBuilder.toString();
    }

    public static void main1(String[] args) {
        try {
            GetVerificationCode imgDemo = new GetVerificationCode();
            //System.out.println(imgDemo.getClass().getResource("/").getPath());
            System.out.println(imgDemo.imgDemo3(new FileOutputStream(imgDemo.getClass().getResource("/").getPath() + "test.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}