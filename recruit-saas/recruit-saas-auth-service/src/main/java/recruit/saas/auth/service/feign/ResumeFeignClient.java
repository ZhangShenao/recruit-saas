package recruit.saas.auth.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import recruit.saas.api.resume.ResumeAPI;

/**
 * @author ZhangShenao
 * @date 2023/4/17 10:35 AM
 * Description 简历服务Feign客户端
 */
@FeignClient("recruit-saas-work-service")
@Component
public interface ResumeFeignClient extends ResumeAPI {
}
