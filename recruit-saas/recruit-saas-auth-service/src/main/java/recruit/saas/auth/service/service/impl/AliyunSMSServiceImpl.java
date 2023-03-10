package recruit.saas.auth.service.service.impl;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import recruit.saas.auth.service.service.SMSService;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author ZhangShenao
 * @date 2023/2/21 5:30 PM
 * Description 阿里云短信服务
 */
@Service
@Slf4j
@RefreshScope   //监听属性动态刷新
public class AliyunSMSServiceImpl implements SMSService {
    //从配置中心获取阿里云Access信息
    @Value("${aliyun.access-key.id}")
    private String accessKeyId;

    @Value("${aliyun.access-key.secrete}")
    private String accessKeySecrete;

    @Override
    @Retryable(include = {Exception.class}, //开启重试:抛出Exception异常自动重试
            maxAttempts = 5,    //最大重试次数
            backoff = @Backoff(delay = 1000L, multiplier = 2))   //回退策略:重试间隔为200ms,且间隔时间倍数为2
    public boolean sendSMS(String mobile, String templateId, String templateParamJson) {
        log.info("Send SMS, Current Time: {}, Thread: {}", LocalDateTime.now(), Thread.currentThread().getName());
//        throw new RuntimeException("发送短信失败");//模拟发送短信异常后自动重试
        StaticCredentialProvider provider = buildCredentialProvider();
        try (AsyncClient client = buildAsyncClient(provider)) {
            // Configure the Client
            // Region ID
            //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
            //.serviceConfiguration(Configuration.create()) // Service-level configuration
            // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
            //.setConnectTimeout(Duration.ofSeconds(30))
            SendSmsRequest sendSmsRequest = buildSmsReq(mobile, templateId, templateParamJson);
         /*   CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
            // Synchronously get the return value of the API request
            SendSmsResponse resp = response.get();
            log.info("Send SMS Result: {}", new Gson().toJson(resp));*/
            return true;
        } catch (Exception e) {
            log.error("Send SMS Error! mobile: {}, templateId: {}", mobile, templateId, e);
            return false;
        }
    }

    @Override
    @Async(value = "smsThreadPool")  //异步处理,并指定线程池。异步方法的返回值可以定义为Future
    @Retryable(include = {Exception.class}, //开启重试:抛出Exception异常自动重试
            maxAttempts = 5,    //最大重试次数
            backoff = @Backoff(delay = 1000L, multiplier = 2))   //回退策略:重试间隔为200ms,且间隔时间倍数为2
    public Future<Boolean> sendSMSAsync(String mobile, String templateId, String templateParamJson) {
        log.info("Send SMS Async. Current Time: {}, Current Thread: {}", LocalDateTime.now(), Thread.currentThread().getName());
//        throw new RuntimeException("发送短信失败");//模拟发送短信异常后自动重试
        StaticCredentialProvider provider = buildCredentialProvider();
        try (AsyncClient client = buildAsyncClient(provider)) {
            // Configure the Client
            // Region ID
            //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
            //.serviceConfiguration(Configuration.create()) // Service-level configuration
            // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
            //.setConnectTimeout(Duration.ofSeconds(30))
//            SendSmsRequest sendSmsRequest = buildSmsReq(mobile, templateId, templateParamJson);
//            CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
//            // Synchronously get the return value of the API request
//            SendSmsResponse resp = response.get();
//            log.info("Send SMS Result: {}", new Gson().toJson(resp));
            return null;
        } catch (Exception e) {
            log.error("Send SMS Error! mobile: {}, templateId: {}", mobile, templateId, e);
            return null;
        }
    }

    //重试失败的降级处理
    //@Recover方法必须和@Retryable方法在同一个类中,否则无法识别
    //此注解注释的方法参数一定要是@Retryable抛出的异常,否则无法识别
    @Recover
    public boolean sendSMSRecover(Exception e, String mobile, String templateId, String templateParamJson) {
        log.error("Send SMS Error! Auto Recover. mobile: {}, templateId: {}, templateParamJson: {}",
                mobile, templateId, templateParamJson, e);
        return false;
    }

    //重试失败的降级处理
    //@Recover方法必须和@Retryable方法在同一个类中,且参数列表与返回值都要一致否则无法识别
    //@Recover方法的第一个参数可以接收重试的Exception,但是一定要是@Retryable抛出的异常,否则无法识别
    @Recover
    public Future<Boolean> sendSMSAsyncRecover(Exception e, String mobile, String templateId, String templateParamJson) {
        log.error("Send SMS Async Error! Auto Recover. mobile: {}, templateId: {}, templateParamJson: {}",
                mobile, templateId, templateParamJson, e);
        return null;
    }

    private StaticCredentialProvider buildCredentialProvider() {
        return StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecrete)
                //.securityToken("<your-token>") // use STS token
                .build());
    }

    private AsyncClient buildAsyncClient(StaticCredentialProvider provider) {
        return AsyncClient.builder()
                .region("cn-hangzhou") // Region ID
                //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                .credentialsProvider(provider)
                //.serviceConfiguration(Configuration.create()) // Service-level configuration
                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                        //.setConnectTimeout(Duration.ofSeconds(30))
                )
                .build();
    }

    private SendSmsRequest buildSmsReq(String mobile, String templateId, String templateParamJson) {
        return SendSmsRequest.builder()
                .phoneNumbers(mobile)
                .signName(SIGNATURE_NAME)
                .templateCode(templateId)
                .templateParam(templateParamJson)
                // Request-level configuration rewrite, can set Http request parameters, etc.
                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                .build();
    }
}
