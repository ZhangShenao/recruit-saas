package recruit.saas.user.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import recruit.saas.common.aspect.PerfLogAspect;

/**
 * @author ZhangShenao
 * @date 2023/3/3 6:15 PM
 * Description 性能日志配置
 */
@Configuration
public class PerfLogConfig {
    @Bean
    public PerfLogAspect perfLogAspect() {
        return new PerfLogAspect();
    }
}
