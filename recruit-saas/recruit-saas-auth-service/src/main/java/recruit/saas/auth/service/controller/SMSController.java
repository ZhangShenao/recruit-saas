package recruit.saas.auth.service.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recruit.saas.auth.service.bo.SMSCodeParamBo;
import recruit.saas.auth.service.param.SendSMSCodeParam;
import recruit.saas.auth.service.service.SMSService;
import recruit.saas.auth.service.utils.IPUtils;
import recruit.saas.common.enums.RedisKeys;
import recruit.saas.common.rest.CommonRestResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

import static recruit.saas.common.constants.SMSConstants.*;
import static recruit.saas.common.enums.RedisKeys.*;

/**
 * @author ZhangShenao
 * @date 2023/2/21 5:37 PM
 * Description 短信API
 */
@RestController
@RequestMapping("/sms")
@Slf4j
public class SMSController {
    @Resource
    private SMSService smsService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/code/send")
    public CommonRestResponse<?> sendSMSCode(@Valid @RequestBody SendSMSCodeParam param, HttpServletRequest request) {
        //随机生成短信验证码
        String mobile = param.getMobile();
        String code = RandomStringUtils.randomNumeric(6);

        //将验证码保存至Redis,并设置有效期
        String key = String.format(SMS_CODE.getKey(), mobile);
        stringRedisTemplate.opsForValue().set(key, code, SMS_CODE.getTtlInSec(), TimeUnit.SECONDS);
        SMSCodeParamBo p = new SMSCodeParamBo();
        p.setCode(code);

        //发送短信
        boolean succ = smsService.sendSMS(mobile, "SMS_270955110", new Gson().toJson(p));
        log.info("Send SMS Code. mobile: {}, code: {}, result: {}", mobile, code, succ);
        if (succ) {
            //发送短信成功,设置验证码锁标识,避免重复发送验证码
            String ip = IPUtils.getRequestIp(request);
            String lockKey = String.format(RedisKeys.SMS_CODE_IP_LOCK.getKey(), ip);
            stringRedisTemplate.opsForValue().set(lockKey, SMS_CODE_LOCK_FLAG, SMS_CODE_IP_LOCK.getTtlInSec(), TimeUnit.SECONDS);
        }
        return CommonRestResponse.success(code);
    }
}
