package recruit.saas.auth.service.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ZhangShenao
 * @date 2023/2/24 12:00 PM
 * Description JWT配置类,在Nacos配置中心中维护
 */
@Component
@Data
@RefreshScope   //监听属性动态刷新
public class JWTProperties {
    @Value("${jwt.key}")
    private String key;
}
