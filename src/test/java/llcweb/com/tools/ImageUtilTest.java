package llcweb.com.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtilTest {
    public static void main(String[] args) {
        String randomCode = CodeUtil.getRandomCode(4, null);
        System.out.println(randomCode);
        BufferedImage imageFromCode = ImageUtil.getImageFromCode(randomCode, 100, 50, 3, true,
                Color.WHITE, Color.GRAY, Color.BLACK);
        try {
            File file = new File("d:/test01.jpg");
            ImageIO.write(imageFromCode,"jpg",file);
            System.out.println("成功保存到："+file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("保存失败");
            e.printStackTrace();
        }

    }
}