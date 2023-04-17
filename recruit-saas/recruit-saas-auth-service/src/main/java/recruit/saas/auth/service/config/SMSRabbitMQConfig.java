package recruit.saas.auth.service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhangShenao
 * @date 2023/3/7 2:33 PM
 * Description 短信的RabbitMQ配置
 */
@Configuration
public class SMSRabbitMQConfig {
    public static final String EXCHANGE_NAME = "sms_exchange"; //交换机名称
    public static final String QUEUE_NAME = "sms_queue"; //队列名称

    //定义交换机
    @Bean
    public Exchange smsExchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    //定义队列
    @Bean
    public Queue smsQueue() {
        return QueueBuilder
                .durable(QUEUE_NAME)
                .build();
    }

    //定义交换机和队列的绑定关系
    @Bean
    public Binding smsBinding() {
        return BindingBuilder
                .bind(smsQueue())
                .to(smsExchange())
                .with("recruit.saas.sms.#") //设置路由key
                .noargs();
    }
}
