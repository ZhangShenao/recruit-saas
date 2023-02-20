package recruit.saas.company.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import recruit.saas.common.rest.CommonRestResponse;

/**
 * @author ZhangShenao
 * @date 2023/2/16 11:37 AM
 * Description
 */
@RestController
public class HelloController {
    @Value("${PORT}")
    private int port;

    @GetMapping("/hello")
    public CommonRestResponse<String> hello() {
        return CommonRestResponse.success("Welcome to company service. port: " + port);
    }
}
