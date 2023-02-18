package recruit.saas.company.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ZhangShenao
 * @date 2023/2/16 11:32 AM
 * Description 企业服务启动类
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient  //开启SpringCloud服务注册与发现客户端
public class CompanyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompanyServiceApplication.class, args);
        log.info("Company Service Started~~");
    }
}
