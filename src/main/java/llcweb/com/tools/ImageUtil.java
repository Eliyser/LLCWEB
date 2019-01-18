package llcweb.com.tools;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * @Author haien
 * @Description 图片工具类
 * @Date 2018/9/6
 **/
public class ImageUtil {
    /**
     * @Author haien
     * @Description 判断是否图片
     * @Date 2018/9/6
     * @Param [file]
     * @return boolean
     **/
    public static boolean isImage(MultipartFile file){
        String fileName=file.getOriginalFilename();
        //根据扩展名判断
        if(!fileName.endsWith("jpg")&&!fileName.endsWith("png")&&!fileName.endsWith("gif")){
            return false;
        }

        //根据图片类型判断,防止文件后缀被篡改
        InputStream inputStream = null;
        BufferedImage bi=null;
        try {
            inputStream = file.getInputStream();
            //解码图片文件
            bi=ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //出现不能解码的文件格式
        if(bi==null){
            return false;
        }
        return true;
    }

    /**
     * @Author haien
     * @Description 扭曲图片
     * @Date 2019/1/18
     * @Param [g, w1, h1, color]
     * @return void
     **/
    private static void shearX(Graphics g, int w1, int h1, Color color) {
        Random random=new Random();
        int period = 2;

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)* Math.sin((double) i / (double) period
                    + (2.2831853071795862D * (double) phase)/ (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0); //复制区域，实现平移
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }
    private static void shearY(Graphics g, int w1, int h1, Color color) {
        Random random=new Random();
        int period = random.nextInt(h1/4) + 10; // h1/2+10;越小扭曲程度越小，越不会超出图片范围

        boolean borderGap = true;
        int frames = 20;
        int phase = random.nextInt(2);
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (2.2831853071795862D * (double) phase)/ (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            //copyArea(x,y,width,height,dx,dy);x,y:往左或向下，dx,dy:右或上,只能存在一对x,y;width,height:图片尺寸
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }

    }

    /**
     * @Author haien
     * @Description 生成图片验证码
     * @Date 2019/1/18
     * @Param [textCode验证码, width图片宽, height图片高, interLine干扰线条数,
     *          randomLocation 文字高低是否随机, backColor背景色, lineColor干扰线色, foreColor文字色]
     * @return java.awt.image.BufferedImage
     **/
    public static BufferedImage getImageFromCode(String textCode,int width,int height,int interLine,
                                          boolean randomLocation,Color backColor,
                                          Color lineColor,Color foreColor){
        //创建BufferedImage对象
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); //画板
        Graphics g = image.getGraphics(); //画纸（后续操作都是在画纸上进行）
        Random r=new Random();//随机数生成器
        //绘制背景
        g.setColor(backColor);
        g.fillRect(0,0,width,height);
        //绘制干扰线
        if(interLine>0){
            int x=r.nextInt(4),y=0;
            int x1=width-r.nextInt(4),y1=0;
            for(int i=0;i<interLine;i++){
                g.setColor(lineColor);
                y=r.nextInt(height-r.nextInt(4));
                y1=r.nextInt(height-r.nextInt(4));
                g.drawLine(x,y,x1,y1); //调用drawLine画直线
            }
        }
        //写验证码
        int fsize=(int)(height*0.8);//字体大小为图片高度的80%
        int fx=0;
        int fy=fsize;
        g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,fsize));
        for(int i=0;i<textCode.length();i++){//开始写入
            fy=randomLocation?(int)((Math.random()*0.3+0.6)*height):fy;//每个字符高低是否随机
            g.setColor(foreColor);
            g.drawString(textCode.charAt(i)+"",fx,fy);
            fx+=(width / textCode.length()) * (Math.random() * 0.3 + 0.8); //依据宽度浮动
        }
        //扭曲图片
        shearX(g,width,height,backColor); //将图片水平平移
        shearY(g,width,height,backColor); //垂直平移
        //添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * width * height);//噪点数量
        for (int i = 0; i < area; i++) {
            int xxx = r.nextInt(width);
            int yyy = r.nextInt(height);
            int rgb = r.nextInt(interLine);
            image.setRGB(xxx, yyy, rgb);
        }
        //封笔
        g.dispose();
        return image;
    }
}
























