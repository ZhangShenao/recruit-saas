package recruit.saas.auth.service.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import recruit.saas.auth.service.param.AffirmQRLoginParam;
import recruit.saas.common.context.CurrentContext;
import recruit.saas.common.enums.UsersRole;
import recruit.saas.common.exception.CommonBusinessException;
import recruit.saas.common.rest.CommonRestResponse;
import recruit.saas.common.vo.UsersVO;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static recruit.saas.common.enums.RedisKeys.*;
import static recruit.saas.common.rest.CommonResultCode.*;

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

    //判断二维码Token是否被使用
    //前端会定时轮询该接口,主要用于判断扫码登录的二维码是否过期
    @GetMapping("/is_qr_token_used")
    public CommonRestResponse<Boolean> isQRTokenUsed(@RequestParam("qr_token") String qrToken) {
        String key = String.format(QR_TOKEN_USAGE_FLAG.getKey(), qrToken);
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(value)) { //二维码Token超时未被使用
            return CommonRestResponse.success(false);
        }
        boolean used = (StringUtils.equalsIgnoreCase(QR_TOKEN_USED_FLAG, value));
        return CommonRestResponse.success(used);
    }

    //二维码扫码确认登录
    @PostMapping("/affirm_qr_login")
    public CommonRestResponse<?> affirmQRLogin(@RequestBody @Valid AffirmQRLoginParam param) {
        //校验二维码Token状态
        String qrToken = param.getQrToken();
        String key = String.format(QR_TOKEN_USAGE_FLAG.getKey(), qrToken);
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(value)) {
            throw CommonBusinessException.ofResultCode(QR_TOKEN_EXPIRED);
        }
        boolean used = (StringUtils.equalsIgnoreCase(QR_TOKEN_USED_FLAG, value));
        if (used) {
            throw CommonBusinessException.ofResultCode(QR_TOKEN_INVALID);
        }

        //将二维码Token标记为已使用状态
        stringRedisTemplate.opsForValue().set(key, QR_TOKEN_USED_FLAG);

        //获取登录用户信息
        Optional<UsersVO> currentUser = CurrentContext.getCurrentUser();
        if (!currentUser.isPresent()) {
            throw CommonBusinessException.ofResultCode(NOT_LOGIN);
        }

        //校验是否为HR角色
        UsersVO user = currentUser.get();
        Integer role = user.getRole();
        if (role == null || UsersRole.HR.getCode() != role) {
            throw CommonBusinessException.ofResultCode(ONLY_HR_CAN_LOGIN);
        }
        return CommonRestResponse.success(user);
    }
}
