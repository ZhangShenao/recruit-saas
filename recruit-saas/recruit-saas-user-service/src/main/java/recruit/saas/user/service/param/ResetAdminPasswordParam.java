package recruit.saas.user.service.param;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ZhangShenao
 * @date 2023/3/3 2:26 PM
 * Description 重置管理员密码请求参数
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ResetAdminPasswordParam {
    @NotBlank(message = "账号ID不能为空")
    private String id;  //账号ID

    @NotBlank(message = "原始密码不能为空")
    private String originPassword;  //原始密码

    @NotBlank(message = "新密码不能为空")
    private String newPassword; //新密码

    @NotBlank(message = "确认新密码不能为空")
    private String affirmNewPassword;   //确认新密码
}
