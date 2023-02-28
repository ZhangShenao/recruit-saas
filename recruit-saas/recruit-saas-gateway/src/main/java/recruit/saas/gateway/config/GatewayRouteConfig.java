package recruit.saas.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhangShenao
 * @date 2023/2/27 10:42 AM
 * Description 网关路由配置。这里配置的都是静态路由
 */
@Configuration
public class GatewayRouteConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service-router",   //设置路由ID,全局唯一
                        //设置路由谓词规则
                        route -> route
                                .order(1)   //设置路由优先级,数字越小优先级越高
                                .path("/gateway/user/**")   //匹配请求路径
                                .filters(f -> f.stripPrefix(1))    //设置过滤器,删除路径上的第一个前缀
                                .uri("lb://recruit-saas-user-service")  //转发的目标地址
                )
                .route("company-service-router",
                        route -> route
                                .order(1)
                                .path("/gateway/company/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://recruit-saas-company-service")
                )
                .route("auth-service-router",
                        route -> route
                                .order(1)
                                .path("/gateway/auth/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://recruit-saas-auth-service")
                ).build();
    }
}
