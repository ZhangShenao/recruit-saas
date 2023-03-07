package recruit.saas.auth.service.bo;

import lombok.Builder;
import lombok.Data;

/**
 * @author ZhangShenao
 * @date 2023/3/7 2:43 PM
 * Description 发送短信BO
 */
@Data
@Builder
public class SendSMSBO {
    private String mobile;  //手机号
    private String templateId;  //短信模板ID
    private String templateParamJson;   //模板参数json
}
