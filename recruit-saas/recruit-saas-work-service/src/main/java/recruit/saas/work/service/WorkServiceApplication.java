package recruit.saas.work.service;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ZhangShenao
 * @date 2023/4/17 9:17 AM
 * Description 工作服务启动类
 */
@SpringBootApplication
@Slf4j
@MapperScan(basePackages = "recruit.saas.work.service.mapper")  //开启MyBatis Mapper扫描
@EnableDiscoveryClient  //开启SpringCloud服务注册与发现客户端
public class WorkServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkServiceApplication.class, args);
        log.info("Work Service Started~~");
    }
}
