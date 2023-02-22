package recruit.saas.auth.service.handler;

import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import recruit.saas.common.exception.CommonBusinessException;
import recruit.saas.common.rest.CommonRestResponse;

import java.util.List;
import java.util.Map;

import static recruit.saas.common.rest.CommonResultCode.INVALID_PARAM;

/**
 * @author ZhangShenao
 * @date 2023/2/22 1:43 PM
 * Description 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    //捕获通用业务异常
    @ResponseBody
    @ExceptionHandler(CommonBusinessException.class)
    public CommonRestResponse<?> onBusinessException(CommonBusinessException e) {
        return CommonRestResponse.ofBusinessException(e);
    }

    //捕获请求参数异常
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonRestResponse<?> onArgNotValid(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        if (CollectionUtils.isEmpty(bindingResult.getFieldErrors())) {
            return CommonRestResponse.success();
        }
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        Map<String, String> errors = Maps.newHashMapWithExpectedSize(fieldErrors.size());
        bindingResult.getFieldErrors().forEach(x -> errors.put(x.getField(), x.getDefaultMessage()));
        return CommonRestResponse.fail(INVALID_PARAM, errors);
    }
}
