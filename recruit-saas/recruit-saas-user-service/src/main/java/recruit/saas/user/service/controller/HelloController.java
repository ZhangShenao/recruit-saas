package recruit.saas.user.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import recruit.saas.common.rest.CommonRestResponse;

/**
 * @author ZhangShenao
 * @date 2023/2/16 11:36 AM
 * Description
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public CommonRestResponse hello() {
        return CommonRestResponse.success("Welcome to user service");
    }
}
