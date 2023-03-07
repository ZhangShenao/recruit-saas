package recruit.saas.auth.service.consumer;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import recruit.saas.auth.service.bo.SendSMSBO;
import recruit.saas.auth.service.config.SMSRabbitMQConfig;
import recruit.saas.auth.service.service.SMSService;

import javax.annotation.Resource;

/**
 * @author ZhangShenao
 * @date 2023/3/7 2:47 PM
 * Description 短信业务的RabbitMQ消费者
 */
@Component
@Slf4j
public class SMSRabbitMQConsumer {
    @Resource
    private SMSService smsService;

    //注册RabbitMQ消息监听器
    @RabbitListener(queues = {SMSRabbitMQConfig.QUEUE_NAME})
    public void onSMSMessage(String payload, Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        log.info("Receive SMS Message. payload: {}, routingKey: {}", payload, routingKey);
        SendSMSBO sendSMSBO = new Gson().fromJson(payload, SendSMSBO.class);
        smsService.sendSMSAsync(sendSMSBO.getMobile(), sendSMSBO.getTemplateId(), sendSMSBO.getTemplateParamJson());
    }
}
