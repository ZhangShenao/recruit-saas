package recruit.saas.user.service.param;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @author ZhangShenao
 * @date 2023/3/3 10:47 AM
 * Description 分配管理员账号请求参数
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AssignAdminParam {
    @NotBlank(message = "用户名不能为空")
    private String username;    //用户名

    @NotBlank(message = "密码名不能为空")
    private String password;    //密码

    private String remark;  //备注
}
