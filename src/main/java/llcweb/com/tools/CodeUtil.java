package llcweb.com.tools;

import java.util.Random;

public class CodeUtil {
    /**
     * 生成随机验证码:数字+字母
     * @param length   长度
     * @param exChars  排除的字符
     * @return
     */
    public static String getRandomCode(int length,String exChars) {
        int i=0;
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        while (i < length) {
            int t = random.nextInt(123);
            if ((t >= 97 || (t >= 65 && t <= 90) || (t >= 48 && t <= 57)) && (exChars == null || exChars.indexOf((char) t) < 0)) {
                sb.append((char) t);
                i++;
            }
        }
        return sb.toString();
    }

}
