package recruit.saas.auth.service.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import recruit.saas.auth.service.utils.IPUtils;
import recruit.saas.common.constants.RedisKeys;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangShenao
 * @date 2023/2/21 7:02 PM
 * Description 短信验证码拦截器
 * 当验证码还在有效期内时,禁止用户再次请求发送验证码,防止恶意攻击
 */
@Slf4j
public class SMSCodeInterceptor implements HandlerInterceptor {
    private static final String LOCK_FLAG = "1";

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
        if (LOCK_FLAG.equals(value)) {
            log.error("验证码还在有效期内,请勿重复获取!");
            return false;
        }

        //验证码已失效,设置锁标识,并将请求放行
        stringRedisTemplate.opsForValue().set(key, LOCK_FLAG, RedisKeys.SMS_CODE_IP_LOCK.getTtlInSec(), TimeUnit.SECONDS);
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
