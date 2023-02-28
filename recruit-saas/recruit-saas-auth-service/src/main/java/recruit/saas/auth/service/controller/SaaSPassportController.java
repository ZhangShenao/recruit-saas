package recruit.saas.auth.service.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recruit.saas.common.rest.CommonRestResponse;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static recruit.saas.common.enums.RedisKeys.*;

/**
 * @author ZhangShenao
 * @date 2023/2/28 4:27 PM
 * Description SaaS企业端注册与登录API
 */
@RestController
@RequestMapping("/saas/passport")
public class SaaSPassportController {
    private static final String QR_TOKEN_NOT_USED_FLAG = "0";   //二维码token未被使用标识
    private static final String QR_TOKEN_USED_FLAG = "1";   //二维码token已被使用标识

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //获取二维码对应的唯一Token,每次进入企业端或者刷新页面时请求
    @GetMapping("/qr_token")
    public CommonRestResponse<String> generateQRToken() {
        String token = UUID.randomUUID().toString();

        //将二维码Token暂存至Redis,并标记为未被使用
        String key = String.format(QR_TOKEN_USAGE_FLAG.getKey(), token);
        stringRedisTemplate.opsForValue().set(key, QR_TOKEN_NOT_USED_FLAG, QR_TOKEN_USAGE_FLAG.getTtlInSec(), TimeUnit.SECONDS);
        return CommonRestResponse.success(token);
    }
}
