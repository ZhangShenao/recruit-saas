package recruit.saas.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ZhangShenao
 * @date 2023/2/20 10:25 AM
 * Description 网关服务启动类
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient  //网关也作为一个服务注册
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        log.info("Gateway Service Started~~");
    }
}
