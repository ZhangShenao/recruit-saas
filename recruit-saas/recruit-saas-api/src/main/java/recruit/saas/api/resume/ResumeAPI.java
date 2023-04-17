package recruit.saas.api.resume;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import recruit.saas.common.rest.CommonRestResponse;

/**
 * @author ZhangShenao
 * @date 2023/4/17 9:31 AM
 * Description 简历服务Feign客户端
 */
public interface ResumeAPI {
    /**
     * 初始化简历
     *
     * @param userId 用户ID
     * @return 简历ID
     */
    @PostMapping("/work/resume/init")
    CommonRestResponse<String> initResume(@RequestParam("user_id") String userId);
}
