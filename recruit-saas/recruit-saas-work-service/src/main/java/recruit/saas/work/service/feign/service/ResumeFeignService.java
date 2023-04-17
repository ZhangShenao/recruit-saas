package recruit.saas.work.service.feign.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import recruit.saas.api.resume.ResumeAPI;
import recruit.saas.common.rest.CommonRestResponse;
import recruit.saas.work.service.service.ResumeService;

import javax.annotation.Resource;

/**
 * @author ZhangShenao
 * @date 2023/4/17 9:34 AM
 * Description 简历服务Feign服务端
 */
@RestController
@Slf4j
public class ResumeFeignService implements ResumeAPI {
    @Resource
    private ResumeService resumeService;

    @PostMapping("/resume/init")
    @Override
    public CommonRestResponse<String> initResume(String userId) {
        String resumeId = resumeService.initResume(userId);
        return CommonRestResponse.success(resumeId);
    }
}
