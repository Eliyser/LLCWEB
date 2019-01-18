package llcweb.com.controller.admin;

import llcweb.com.tools.CodeUtil;
import llcweb.com.tools.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author haien
 * @Description 处理登录过程的请求
 * @Date 2019/1/18
 **/
@Controller
public class LoginController {
    /**
     * @Author haien
     * @Description 获取图片验证码
     * @Date 2019/1/18
     * @Param [request, response]
     * @return void
     **/
    @RequestMapping(value="/getImageCode",method = RequestMethod.GET)
    public Map<String,Object> getImageCode(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map=new HashMap<>();
        try {
            // 设置浏览器不缓存本页
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Expires", "0");
            // 生成验证码，写入用户session
            String verifyCode = CodeUtil.getRandomCode(4, null);
            request.getSession().setAttribute("imageVerify", verifyCode);
            // 输出验证码给客户端
            response.setContentType("image/jpeg");
            BufferedImage bim = ImageUtil
                    .getImageFromCode(verifyCode, 80, 40, 3,
                            true, Color.WHITE, Color.GRAY, Color.BLACK);
            //尺寸合适，太窄太扁字符在平移时会覆盖，也容易出线
            ImageIO.write(bim, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result",0);
            map.put("message","获取客户端输出流失败！");
            return map;
        }
        map.put("result",1);
        return map;
    }
}
