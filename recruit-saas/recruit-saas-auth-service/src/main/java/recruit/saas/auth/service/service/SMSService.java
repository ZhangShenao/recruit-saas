package recruit.saas.auth.service.service;

import java.util.concurrent.Future;

/**
 * @author ZhangShenao
 * @date 2023/2/21 5:29 PM
 * Description 短信服务接口
 */
public interface SMSService {
    String SIGNATURE_NAME = "蹲个工作网";    //短信签名

    /**
     * 发送短信
     *
     * @param mobile            手机号
     * @param templateId        短信模板ID
     * @param templateParamJson 短信模板参数（json格式）
     * @return 是否发送成功
     */
    boolean sendSMS(String mobile, String templateId, String templateParamJson);

    /**
     * 异步发送短信
     *
     * @param mobile            手机号
     * @param templateId        短信模板ID
     * @param templateParamJson 短信模板参数（json格式）
     * @return 异步发送结果
     */
    Future<Boolean> sendSMSAsync(String mobile, String templateId, String templateParamJson);
}
