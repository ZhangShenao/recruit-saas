package recruit.saas.gateway.filter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import recruit.saas.common.enums.JWTPlatformType;
import recruit.saas.common.exception.CommonBusinessException;
import recruit.saas.common.rest.CommonResultCode;
import recruit.saas.gateway.component.JWTParser;
import recruit.saas.gateway.props.JWTSkipAuthProperties;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

/**
 * @author ZhangShenao
 * @date 2023/2/25 4:34 PM
 * Description JWT鉴权过滤器
 * GlobalFilter全局过滤器,对所有路由均生效
 */
@Slf4j
@Component
public class JWTAuthFilter implements GlobalFilter, Ordered, WriteErrorResponseFilter {
    @Resource
    private JWTSkipAuthProperties skipAuthProperties;

    @Resource
    private JWTParser jwtParser;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();   //Ant风格的路径匹配器

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("JWTAuthFilter#filter");
        //对于需要跳过鉴权的请求,直接放行
        String path = exchange.getRequest().getURI().getPath();
        Optional<String> excluded = Optional.ofNullable(skipAuthProperties.getUrls())
                .orElse(Collections.emptyList())
                .stream()
                .filter(x -> antPathMatcher.match(x, path))
                .findAny();
        if (excluded.isPresent()) {
            log.info("Skip JWT Auth for Path: {}", path);
            return chain.filter(exchange);
        }

        //解析用户Token
        log.info("Process JWT Auth For Path: {}", path);
        String jwtToken = exchange.getRequest().getHeaders().getFirst(JWTPlatformType.TOKEN_HEADER_KEY);
        if (StringUtils.isBlank(jwtToken)) {
            return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.NOT_LOGIN));
        }
        String[] split = StringUtils.split(jwtToken, JWTPlatformType.TOKEN_PREFIX_DELIMITER);
        if (split == null || split.length != 2) {
            return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.NOT_LOGIN));
        }

        String prefix = split[0];
        String token = split[1];
        if (StringUtils.isBlank(prefix) || StringUtils.isBlank(token)) {
            return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.INVALID_TOKEN));
        }

        //将用户信息写入Header
        return writeUserInfoToHeader(exchange, chain, prefix, token);
    }

    //配置过滤器优先级,order数字越小,优先级越高
    @Override
    public int getOrder() {
        return 1;
    }

    //根据JWT Token 解析用户信息,并写入Request Header
    private Mono<Void> writeUserInfoToHeader(ServerWebExchange exchange, GatewayFilterChain chain, String prefix, String token) {
        try {
            String payload = jwtParser.parseJWTToken(token);
            if (StringUtils.isBlank(payload)) {
                return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.INVALID_TOKEN));
            }
            ServerWebExchange newExchange = exchange;
            //根据不同平台类型,将用户信息写入对应的Request Header,便于向下游传递
            if (JWTPlatformType.APP.getTokenPrefix().equalsIgnoreCase(prefix)) {
                newExchange = buildExchangeWithNewHeader(exchange, JWTPlatformType.APP.getUserHeaderKey(), payload);
            } else if (JWTPlatformType.SAAS.getTokenPrefix().equalsIgnoreCase(prefix)) {
                newExchange = buildExchangeWithNewHeader(exchange, JWTPlatformType.SAAS.getUserHeaderKey(), payload);
            } else if (JWTPlatformType.ADMIN.getTokenPrefix().equalsIgnoreCase(prefix)) {
                newExchange = buildExchangeWithNewHeader(exchange, JWTPlatformType.ADMIN.getUserHeaderKey(), payload);
            }

            //请求放行
            return chain.filter(newExchange);
        } catch (ExpiredJwtException eje) {
            log.error("JWT Token Expired! {}", token);
            return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.TOKEN_EXPIRED));
        } catch (Exception e) {
            log.error("Parse JWT Token Error!", e);
            return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.INVALID_TOKEN));
        }
    }

    //根据Header的key-value,构造新的ServerWebExchange
    private ServerWebExchange buildExchangeWithNewHeader(ServerWebExchange exchange, String key, String value) {
        try {
            //构造新的Request
            ServerHttpRequest req = exchange.getRequest()
                    .mutate()
                    .header(key, URLEncoder.encode(value, StandardCharsets.UTF_8.name()))   //对Header Value进行URL编码,防止中文乱码
                    .build();

            //根据Request构造新的ServerWebExchange
            return exchange
                    .mutate()
                    .request(req)
                    .build();
        } catch (Exception e) {
            log.error("Build Exchange With New Header Error!", e);
            return exchange;
        }
    }
}
