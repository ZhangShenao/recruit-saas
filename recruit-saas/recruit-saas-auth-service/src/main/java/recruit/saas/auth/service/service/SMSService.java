package recruit.saas.auth.service.service;

/**
 * @author ZhangShenao
 * @date 2023/2/21 5:29 PM
 * Description 短信服务接口
 */
public interface SMSService {
    /**
     * 发送短信
     *
     * @param mobile     手机号
     * @param templateId 短信模板ID
     * @return 是否发送成功
     */
    /**
     * 发送短信
     *
     * @param mobile            手机号
     * @param templateId        短信模板ID
     * @param templateParamJson 短信模板参数（json格式）
     * @return 是否发送成功
     */
    boolean sendSMS(String mobile, String templateId, String templateParamJson);
}
