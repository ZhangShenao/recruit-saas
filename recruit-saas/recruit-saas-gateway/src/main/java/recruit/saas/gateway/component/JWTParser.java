package recruit.saas.gateway.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import recruit.saas.gateway.props.JWTProperties;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.crypto.SecretKey;

/**
 * @author ZhangShenao
 * @date 2023/2/25 5:41 PM
 * Description JWT解析器
 */
@Component
@Slf4j
public class JWTParser {
    @Resource
    private JWTProperties props;


    /**
     * 解析JWT Token
     *
     * @param token JWT Token
     * @return 业务payload
     */
    public String parseJWTToken(String token) throws Exception {
        String key = props.getKey();
        //对签名进行Base64编码
        String encodedKey = new BASE64Encoder().encode(key.getBytes());

        //根据加密后的签名,生成秘钥key
        SecretKey secretKey = Keys.hmacShaKeyFor(encodedKey.getBytes());

        //创建JwtParser
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();

        //解析JWT Token
        //如果解析抛出异常,说明token已经被篡改,校验不通过
        Jws<Claims> claims = jwtParser.parseClaimsJws(token);

        //获取payload
        return claims.getBody().getSubject();
    }
}
