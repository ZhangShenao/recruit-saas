package recruit.saas.common.utils;

import org.springframework.util.DigestUtils;

/**
 * @author ZhangShenao
 * @date 2023/3/2 10:40 AM
 * Description MD5编解码工具类
 */
public class MD5Utils {
    /**
     * MD5混合加密
     * @param data 待加密字符串
     * @param salt 盐，用于混合md5加密
     * @return 加密后字符串
     */
    public static String encrypt(String data, String salt) {
        String base = data + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
