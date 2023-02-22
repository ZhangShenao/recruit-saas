package recruit.saas.auth.service.param;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author ZhangShenao
 * @date 2023/2/22 2:24 PM
 * Description 手机号验证码登录请求参数
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MobileLoginParam {
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号不符合要求")
    private String mobile;  //手机号

    @NotBlank(message = "验证码不能为空")
    @Length(min = 6, max = 6, message = "验证码不符合要求")
    private String code;    //验证码
}
