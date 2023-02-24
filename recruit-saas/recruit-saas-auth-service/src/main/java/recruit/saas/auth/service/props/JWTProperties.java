package recruit.saas.auth.service.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ZhangShenao
 * @date 2023/2/24 12:00 PM
 * Description JWT配置类
 */
@PropertySource("classpath:jwt.properties")
@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class JWTProperties {
    private String key;
}
