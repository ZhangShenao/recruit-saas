package recruit.saas.user.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import recruit.saas.common.filter.CurrentContextInterceptor;

/**
 * @author ZhangShenao
 * @date 2023/2/26 11:26 AM
 * Description Web MVC 配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public CurrentContextInterceptor currentContextInterceptor() {
        return new CurrentContextInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册请求上下文拦截器
        registry.addInterceptor(currentContextInterceptor())
                .addPathPatterns("/**");
    }
}
