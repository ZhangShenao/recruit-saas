package recruit.saas.auth.service.param;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author ZhangShenao
 * @date 2023/2/21 5:46 PM
 * Description 发送短信验证码请求参数
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendSMSCodeParam {
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号不符合要求")   //validation参数校验
    private String mobile;  //手机号
}
