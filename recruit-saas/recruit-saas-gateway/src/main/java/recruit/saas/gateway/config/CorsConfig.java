package recruit.saas.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author ZhangShenao
 * @date 2023/2/28 7:48 PM
 * Description 跨域配置,作用等效于在yaml文件中的globalcors配置
 */
@Configuration
public class CorsConfig {
    //注册跨域过滤器
    @Bean
    public CorsWebFilter corsWebFilter() {
        //设置跨域属性
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");    //授信来源地址
        config.setAllowCredentials(true);   //跨域请求是否携带Cookie，默认为false
        config.addAllowedMethod("*"); //可以被接收的Request Header属性
        config.addAllowedHeader("*");   //允许跨域的HttpMethod
        config.addExposedHeader("*");   //允许被暴露出去的Response Header响应头

        //创建UrlBasedCorsConfigurationSource,添加映射路径,并注册跨域配置
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        //注册CorsWebFilter
        return new CorsWebFilter(source);
    }
}
