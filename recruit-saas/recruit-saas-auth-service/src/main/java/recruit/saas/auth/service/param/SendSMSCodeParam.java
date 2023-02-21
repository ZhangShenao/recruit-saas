package recruit.saas.auth.service.param;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ZhangShenao
 * @date 2023/2/21 5:46 PM
 * Description 发送短信验证码请求参数
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendSMSCodeParam {
    private String mobile;  //手机号
}
