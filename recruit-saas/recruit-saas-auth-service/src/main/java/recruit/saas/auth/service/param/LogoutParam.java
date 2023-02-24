package recruit.saas.auth.service.param;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ZhangShenao
 * @date 2023/2/24 12:17 PM
 * Description 退出登录请求参数
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LogoutParam {
    @NotBlank(message = "用户ID不能为空")
    private String userId;  //登录的用户ID
}
