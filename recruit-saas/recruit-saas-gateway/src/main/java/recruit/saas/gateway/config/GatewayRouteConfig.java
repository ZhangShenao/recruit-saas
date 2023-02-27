package recruit.saas.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhangShenao
 * @date 2023/2/27 10:42 AM
 * Description 网关路由配置
 * 这里配置的都是静态路由
 */
@Configuration
public class GatewayRouteConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service-router",   //设置路由ID,全局唯一
                        //设置路由谓词规则
                        route -> route
                                .path("/user/**")
                                .uri("lb://recruit-saas-user-service")
                )
                .route("company-service-router",
                        route -> route
                                .path("/company/**")
                                .uri("lb://recruit-saas-company-service")
                )
                .route("auth-service-router",
                        route -> route
                                .path("/auth/**")
                                .uri("lb://recruit-saas-auth-service")
                ).build();
    }
}
