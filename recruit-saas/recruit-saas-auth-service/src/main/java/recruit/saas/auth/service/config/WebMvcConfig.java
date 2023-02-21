package recruit.saas.auth.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import recruit.saas.auth.service.interceptor.SMSCodeInterceptor;

/**
 * @author ZhangShenao
 * @date 2023/2/21 7:05 PM
 * Description WEB MVC配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public SMSCodeInterceptor smsCodeInterceptor() {
        return new SMSCodeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //验证码拦截器
        registry.addInterceptor(smsCodeInterceptor()).addPathPatterns("/sms/code/send");   //这里路径匹配要去掉全局的context-path,不然会匹配不到)
    }
}
