package recruit.saas.work.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import recruit.saas.common.rest.CommonRestResponse;

/**
 * @author ZhangShenao
 * @date 2023/4/18 10:07 AM
 * Description 健康检查API
 */
@RestController
public class HealthController {
    @Value("${server.port}")
    private int port;

    @GetMapping("/health")
    public CommonRestResponse<String> health() {
        String ret = String.format("Service On Port %d is OK!", port);
        return CommonRestResponse.success(ret);
    }
}
