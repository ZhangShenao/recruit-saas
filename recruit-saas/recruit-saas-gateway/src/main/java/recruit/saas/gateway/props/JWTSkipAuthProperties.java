package recruit.saas.gateway.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhangShenao
 * @date 2023/2/25 4:39 PM
 * Description JWT跳过鉴权配置
 */
@ConfigurationProperties(prefix = "exclude")
@Component
@PropertySource("classpath:jwt-auth.properties")
@Data
public class JWTSkipAuthProperties {
    private List<String> urls;
}
