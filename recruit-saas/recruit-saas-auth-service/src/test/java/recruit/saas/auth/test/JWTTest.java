package recruit.saas.auth.test;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKey;

/**
 * @author ZhangShenao
 * @date 2023/2/23 8:13 PM
 * Description JWT单元测试
 */
@SpringBootTest
@SpringBootConfiguration
public class JWTTest {
    private static final String KEY = "zsa-123456789-abcdefghijkl";  //秘钥

    //测试生成Token
    @Test
    public void testGenerateJWTToken() {
        //对签名进行Base64编码
        String encodedKey = new BASE64Encoder().encode(KEY.getBytes());

        //根据加密后的签名,生成秘钥key
        SecretKey secretKey = Keys.hmacShaKeyFor(encodedKey.getBytes());

        //自定义Payload
        Payload payload = new Payload();
        payload.setId("1");
        payload.setName("payload");
        String subject = new Gson().toJson(payload);

        //生成JWT Token
        String token = Jwts.builder()
                .setSubject(subject)
                .signWith(secretKey)
                .compact();
        System.out.println("Generated JWT Token: " + token);
    }

    //校验JWT Token是否有效
    @Test
    public void testCheckJWTToken() {
        //从前端/客户端获取Token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJpZFwiOlwiMVwiLFwibmFtZVwiOlwicGF5bG9hZFwifSJ9.sY3PmXT4f1mlNu9B28a-YNmJgoY_5IToWTNrRTyITyE";

        //对签名进行Base64编码
        String encodedKey = new BASE64Encoder().encode(KEY.getBytes());

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
        String payload = claims.getBody().getSubject();
        Payload p = new Gson().fromJson(payload, Payload.class);
        System.out.println("Parse from JWT: " + p);

    }

    @Data
    @ToString
    private static class Payload {
        private String id;
        private String name;
    }
}
