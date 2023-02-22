package recruit.saas.auth.service.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import recruit.saas.auth.service.utils.IPUtils;
import recruit.saas.common.enums.RedisKeys;
import recruit.saas.common.exception.CommonBusinessException;
import recruit.saas.common.rest.CommonResultCode;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static recruit.saas.common.constants.SMSConstants.SMS_CODE_LOCK_FLAG;

/**
 * @author ZhangShenao
 * @date 2023/2/21 7:02 PM
 * Description 短信验证码拦截器
 * 当验证码还在有效期内时,禁止用户再次请求发送验证码,防止恶意攻击
 */
@Slf4j
public class SMSCodeInterceptor implements HandlerInterceptor {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求IP
        String ip = IPUtils.getRequestIp(request);

        //获取ip锁标识
        String key = String.format(RedisKeys.SMS_CODE_IP_LOCK.getKey(), ip);
        String value = stringRedisTemplate.opsForValue().get(key);

        //验证码未过期,拦截请求防止恶意刷
        if (SMS_CODE_LOCK_FLAG.equals(value)) {
            throw new CommonBusinessException(CommonResultCode.SMS_SNT_TOO_FREQUENTLY);
        }

        //验证码已失效,将请求放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //do nothing
    }
}
