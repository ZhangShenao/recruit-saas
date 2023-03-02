package recruit.saas.gateway.filter;

import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import recruit.saas.common.exception.CommonBusinessException;
import recruit.saas.common.rest.CommonRestResponse;

import java.nio.charset.StandardCharsets;

/**
 * @author ZhangShenao
 * @date 2023/3/1 8:18 PM
 * Description 回写异常响应过滤器
 */
public interface WriteErrorResponseFilter {
    default Mono<Void> writeErrorResponse(ServerWebExchange exchange, CommonBusinessException exception) {
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
}
