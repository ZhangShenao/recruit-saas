package recruit.saas.gateway.filter;

import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import recruit.saas.common.enums.JWTPlatformType;
import recruit.saas.common.exception.CommonBusinessException;
import recruit.saas.common.rest.CommonRestResponse;
import recruit.saas.common.rest.CommonResultCode;
import recruit.saas.gateway.component.JWTParser;
import recruit.saas.gateway.props.JWTSkipAuthProperties;
import recruit.saas.pojo.vo.UsersVO;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

/**
 * @author ZhangShenao
 * @date 2023/2/25 4:34 PM
 * Description JWT鉴权过滤器
 */
@Slf4j
@Component
public class JWTAuthFilter implements GlobalFilter, Ordered {
    @Resource
    private JWTSkipAuthProperties skipAuthProperties;

    @Resource
    private JWTParser jwtParser;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();   //Ant风格的路径匹配器

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
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
        String appToken = exchange.getRequest().getHeaders().getFirst(JWTPlatformType.TOKEN_HEADER_KEY);
        if (StringUtils.isBlank(appToken)) {
            return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.NOT_LOGIN));
        }
        String[] split = StringUtils.split(appToken, JWTPlatformType.TOKEN_PREFIX_DELIMITER);
        if (split == null || split.length != 2) {
            return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.NOT_LOGIN));
        }

        String token = split[1];
        return parseUserInfoFromToken(exchange, chain, token);
    }

    //配置过滤器优先级,order数字越小,优先级越高
    @Override
    public int getOrder() {
        return 0;
    }

    //写回错误信息
    private Mono<Void> writeErrorResponse(ServerWebExchange exchange, CommonBusinessException exception) {
        //构造json类型的通用异常响应
        ServerHttpResponse response = exchange.getResponse();
        CommonRestResponse<?> result = CommonRestResponse.ofBusinessException(exception);
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE);  //Content-Type=application/json
        String jsonBody = new Gson().toJson(result);

        //写回response
        DataBuffer dataBuffer = response.bufferFactory()
                .wrap(jsonBody.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }

    //根据JWT Token 解析用户信息
    private Mono<Void> parseUserInfoFromToken(ServerWebExchange exchange, GatewayFilterChain chain, String token) {
        try {
            String payload = jwtParser.parseJWTToken(token);
            if (StringUtils.isBlank(payload)) {
                return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.INVALID_TOKEN));
            }
            //解析用户信息
            UsersVO usersInfo = new Gson().fromJson(payload, UsersVO.class);

            //将用户信息写入Request Header,向下游传递
            //TODO impl
            
            //请求放行
            return chain.filter(exchange);
        } catch (ExpiredJwtException eje) {
            log.error("JWT Token Expired! {}", token);
            return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.TOKEN_EXPIRED));
        } catch (Exception e) {
            log.error("Parse JWT Token Error!", e);
            return writeErrorResponse(exchange, CommonBusinessException.ofResultCode(CommonResultCode.INVALID_TOKEN));
        }
    }
}
