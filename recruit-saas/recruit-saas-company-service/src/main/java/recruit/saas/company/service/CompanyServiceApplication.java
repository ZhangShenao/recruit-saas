package recruit.saas.company.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZhangShenao
 * @date 2023/2/16 11:32 AM
 * Description 企业服务启动类
 */
@SpringBootApplication
@Slf4j
public class CompanyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompanyServiceApplication.class, args);
        log.info("Company Service Started~~");
    }
}
