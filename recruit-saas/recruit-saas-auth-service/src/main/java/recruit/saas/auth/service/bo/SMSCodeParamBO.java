package recruit.saas.auth.service.bo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @author ZhangShenao
 * @date 2023/2/21 6:10 PM
 * Description 短信验证码参数
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SMSCodeParamBO {
    private String code;    //验证码
}
