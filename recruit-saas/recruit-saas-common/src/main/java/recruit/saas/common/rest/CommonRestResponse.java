package recruit.saas.common.rest;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhangShenao
 * @date 2023/2/16 2:10 PM
 * Description 通用Restful相应
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class CommonRestResponse {
    private int status;  //业务响应状态码,具体定义见recruit.saas.common.rest.CommonResultCode
    private String msg;     //响应消息
    private boolean success;    //是否成功
    private Object data;    // 响应数据,可以是Object,也可以是List或Map等

    public static CommonRestResponse success() {
        return new CommonRestResponse(CommonResultCode.SUCCESS.getStatus(), CommonResultCode.SUCCESS.getMsg(), true, null);
    }

    public static CommonRestResponse success(Object data) {
        return new CommonRestResponse(CommonResultCode.SUCCESS.getStatus(), CommonResultCode.SUCCESS.getMsg(), true, data);
    }

    public static CommonRestResponse fail(CommonResultCode commonResultCode) {
        return new CommonRestResponse(commonResultCode.getStatus(), commonResultCode.getMsg(), false, null);
    }

    public static CommonRestResponse fail(CommonResultCode commonResultCode, Object data) {
        return new CommonRestResponse(commonResultCode.getStatus(), commonResultCode.getMsg(), false, data);
    }

    public static CommonRestResponse ofResultCode(CommonResultCode resultCode) {
        return new CommonRestResponse(resultCode.getStatus(), resultCode.getMsg(), resultCode.isSuccess(), null);
    }

    public static CommonRestResponse ofResultCode(CommonResultCode resultCode, Object data) {
        return new CommonRestResponse(resultCode.getStatus(), resultCode.getMsg(), resultCode.isSuccess(), data);
    }
}
