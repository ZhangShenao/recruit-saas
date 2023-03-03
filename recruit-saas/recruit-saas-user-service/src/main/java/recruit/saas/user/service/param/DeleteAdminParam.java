package recruit.saas.user.service.param;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @author ZhangShenao
 * @date 2023/3/3 10:47 AM
 * Description 删除管理员请求参数
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DeleteAdminParam {
    @NotBlank(message = "账号ID不能为空")
    private String id;    //管理员账号ID
}
