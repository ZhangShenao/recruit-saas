package recruit.saas.auth.service.param;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ZhangShenao
 * @date 2023/3/1 10:03 AM
 * Description 确认扫码登录请求参数
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AffirmQRLoginParam {
    @NotBlank(message = "二维码Token不能为空")
    private String qrToken;
}
