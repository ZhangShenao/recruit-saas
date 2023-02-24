package recruit.saas.auth.service.component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import recruit.saas.auth.service.props.JWTProperties;
import recruit.saas.common.constants.JWTConstants;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangShenao
 * @date 2023/2/24 11:59 AM
 * Description JWT工具组件
 */
@Component
@Slf4j
public class JWTTool {
    @Resource
    private JWTProperties props;

    /**
     * 生成带过期时间和前缀的的JWT Token
     * @param payload 业务字段
     * @param prefix 自定义前缀
     * @param duration 过期时间
     * @param timeUnit 过期时间单位
     * @return token
     */
    public String generateTokenWithExpirationAndPrefix(String payload, String prefix, long duration, TimeUnit timeUnit) {
        return prefix + JWTConstants.TOKEN_PREFIX_DELIMITER + generateTokenWithExpiration(payload, duration, timeUnit);
    }

    /**
     * 生成JWT Token
     *
     * @param payload 业务字段
     * @return token
     */
    public String generateToken(String payload) {
        String key = props.getKey();
        String encodedKey = new BASE64Encoder().encode(key.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(encodedKey.getBytes());
        return Jwts.builder()
                .setSubject(payload)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 生成带过期时间的JWT Token
     * @param payload 业务字段
     * @param duration 过期时间(s)
     * @param timeUnit 过期时间单位
     * @return token
     */
    public String generateTokenWithExpiration(String payload, long duration, TimeUnit timeUnit) {
        String key = props.getKey();
        String encodedKey = new BASE64Encoder().encode(key.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(encodedKey.getBytes());
        long millis = timeUnit.toMillis(duration);
        Date expiration = new Date(System.currentTimeMillis() + millis);
        return Jwts.builder()
                .setExpiration(expiration)
                .setSubject(payload)
                .signWith(secretKey)
                .compact();
    }
}
