package recruit.saas.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import recruit.saas.common.enums.RedisKeys;
import recruit.saas.common.exception.CommonBusinessException;
import recruit.saas.common.rest.CommonResultCode;
import recruit.saas.common.utils.IPUtils;
import recruit.saas.gateway.props.IPLimitProperties;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangShenao
 * @date 2023/3/1 8:16 PM
 * Description IP限流过滤器
 */
@Component
@Slf4j
public class IPLimitFilter implements GlobalFilter, Ordered, WriteErrorResponseFilter {
    private static final String IP_DENY_FLAG_VALUE = "1";
    @Resource
    private IPLimitProperties props;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();   //Ant风格的路径匹配器

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("IPLimitFilter#filter");
        //判断请求URL是否需要限流
        String url = exchange.getRequest().getURI().getPath();
        Optional<String> included = Optional.ofNullable(props.getIncludeUrls())
                .orElse(Collections.emptyList())
                .stream()
                .filter(x -> antPathMatcher.match(x, url))
                .findAny();
        if (!included.isPresent()) {
            return chain.filter(exchange);
        }

        //对黑名单内的IP禁止访问
        String ip = IPUtils.getClientIP(exchange.getRequest());
        List<String> blackList = props.getBlackList();
        if (blackList != null && blackList.contains(ip)) {
            return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.IP_DENIED));
        }

        //对IP进行限流
        return limitIP(ip, exchange, chain);
    }

    /**
     * 对IP进行限流
     * 如果在${timeIntervalSec}秒内,该IP访问次数超过${limitCount},则禁止该IP访问${denySec}秒
     */
    private Mono<Void> limitIP(String ip, ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.判断该IP是否仍在封禁期
        String ipDenyKey = String.format(RedisKeys.IP_DENY_FLAG.getKey(), ip);
        String ipDenyValue = stringRedisTemplate.opsForValue().get(ipDenyKey);
        if (StringUtils.equalsIgnoreCase(IP_DENY_FLAG_VALUE, ipDenyValue)) {
            return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.IP_DENIED));
        }

        //记录IP访问次数
        String ipVisitCountKey = String.format(RedisKeys.IP_VISIT_COUNT.getKey(), ip);
        Long count = stringRedisTemplate.opsForValue().increment(ipVisitCountKey);
        if (count == null) {
            log.error("Redis Value Empty! key: {}", ipVisitCountKey);
            return chain.filter(exchange);
        }
        if (count == 1L) {  //首次访问,记录统计时间
            stringRedisTemplate.expire(ipVisitCountKey, props.getTimeIntervalSec(), TimeUnit.SECONDS);
        }

        //如果IP访问次数超出上限,则禁止访问
        if (count > props.getLimitCount()) {
            stringRedisTemplate.opsForValue().set(ipDenyKey, IP_DENY_FLAG_VALUE, props.getDenySec(), TimeUnit.SECONDS);
            return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.REQUEST_TOO_FREQUENT));
        }

        //正常访问放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
