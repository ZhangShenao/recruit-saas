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
import org.springframework.stereotype.Service;
import recruit.saas.auth.service.service.SMSService;

import java.util.concurrent.CompletableFuture;

/**
 * @author ZhangShenao
 * @date 2023/2/21 5:30 PM
 * Description 阿里云短信服务
 */
@Service
@Slf4j
@RefreshScope   //监听属性动态刷新
public class AliyunSMSService implements SMSService {
    //从配置中心获取阿里云Access信息
    @Value("${aliyun.access-key.id}")
    private String accessKeyId;

    @Value("${aliyun.access-key.secrete}")
    private String accessKeySecrete;

    @Override
    public boolean sendSMS(String mobile, String templateId, String templateParamJson) {
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecrete)
                //.securityToken("<your-token>") // use STS token
                .build());
        try (AsyncClient client = AsyncClient.builder()
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
                .build()) {
            // Configure the Client
            // Region ID
            //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
            //.serviceConfiguration(Configuration.create()) // Service-level configuration
            // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
            //.setConnectTimeout(Duration.ofSeconds(30))
            SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                    .phoneNumbers(mobile)
                    .signName(SIGNATURE_NAME)
                    .templateCode(templateId)
                    .templateParam(templateParamJson)
                    // Request-level configuration rewrite, can set Http request parameters, etc.
                    // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                    .build();
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
}
