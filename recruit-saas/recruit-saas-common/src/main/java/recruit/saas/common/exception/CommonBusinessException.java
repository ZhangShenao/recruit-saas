package recruit.saas.common.exception;

import lombok.Data;
import recruit.saas.common.rest.CommonResultCode;

/**
 * @author ZhangShenao
 * @date 2023/2/22 1:37 PM
 * Description 通用业务异常
 */
public class CommonBusinessException extends RuntimeException {
    private CommonResultCode code;

    public CommonBusinessException(CommonResultCode code) {
        super("result code: " + code.getStatus() + ", message: " + code.getMsg());
        this.code = code;
    }

    public CommonResultCode getCode() {
        return code;
    }

    public void setCode(CommonResultCode code) {
        this.code = code;
    }
}
