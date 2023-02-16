package recruit.saas.company.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhangShenao
 * @date 2023/2/16 11:37 AM
 * Description
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Welcome to company service";
    }
}
