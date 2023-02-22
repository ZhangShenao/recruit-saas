package recruit.saas.auth.service.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import recruit.saas.common.exception.CommonBusinessException;
import recruit.saas.common.rest.CommonRestResponse;

/**
 * @author ZhangShenao
 * @date 2023/2/22 1:43 PM
 * Description 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(CommonBusinessException.class)
    public CommonRestResponse<?> onBusinessException(CommonBusinessException e) {
        return CommonRestResponse.ofBusinessException(e);
    }
}
