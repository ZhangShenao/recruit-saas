package recruit.saas.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ZhangShenao
 * @date 2023/2/21 5:11 PM
 * Description 授权服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "recruit.saas.auth.service.mapper")  //开启MyBatis Mapper扫描
@Slf4j
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
        log.info("Auth Service Started~~");
    }
}