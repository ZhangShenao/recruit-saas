package recruit.saas.common.rest;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import recruit.saas.common.exception.CommonBusinessException;

/**
 * @author ZhangShenao
 * @date 2023/2/16 2:10 PM
 * Description 通用Restful相应
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class CommonRestResponse<T> {
    private int status;  //业务响应状态码,具体定义见recruit.saas.common.rest.CommonResultCode
    private String msg;     //响应消息
    private boolean success;    //是否成功
    private T data;    // 响应数据,可以是Object,也可以是List或Map等

    public static <T> CommonRestResponse<T> success() {
        return new CommonRestResponse<>(CommonResultCode.SUCCESS.getStatus(), CommonResultCode.SUCCESS.getMsg(), true, null);
    }

    public static <T> CommonRestResponse<T> success(T data) {
        return new CommonRestResponse<>(CommonResultCode.SUCCESS.getStatus(), CommonResultCode.SUCCESS.getMsg(), true, data);
    }

    public static <T> CommonRestResponse<T> fail(CommonResultCode commonResultCode) {
        return new CommonRestResponse<>(commonResultCode.getStatus(), commonResultCode.getMsg(), false, null);
    }

    public static <T> CommonRestResponse<T> fail(CommonResultCode commonResultCode, T data) {
        return new CommonRestResponse<>(commonResultCode.getStatus(), commonResultCode.getMsg(), false, data);
    }

    public static <T> CommonRestResponse<T> ofResultCode(CommonResultCode resultCode) {
        return new CommonRestResponse<>(resultCode.getStatus(), resultCode.getMsg(), resultCode.isSuccess(), null);
    }

    public static <T> CommonRestResponse<T> ofResultCode(CommonResultCode resultCode, T data) {
        return new CommonRestResponse<>(resultCode.getStatus(), resultCode.getMsg(), resultCode.isSuccess(), data);
    }

    public static <T> CommonRestResponse<T> ofBusinessException(CommonBusinessException exception) {
        CommonResultCode resultCode = exception.getCode();
        return new CommonRestResponse<>(resultCode.getStatus(), resultCode.getMsg(), resultCode.isSuccess(), null);
    }
}
