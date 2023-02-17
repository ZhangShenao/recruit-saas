package recruit.saas.user.service;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZhangShenao
 * @date 2023/2/16 11:30 AM
 * Description 用户服务启动类
 */
@SpringBootApplication
@Slf4j
@MapperScan(basePackages = "recruit.saas.user.service.mapper")  //开启MyBatis Mapper扫描
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
        log.info("Company Service Started~~");
    }
}
