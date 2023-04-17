package recruit.saas.work.service.service;

/**
 * @author ZhangShenao
 * @date 2023/4/17 9:35 AM
 * Description 简历Service
 */
public interface ResumeService {
    /**
     * 初始化建立
     *
     * @param userId 用户ID
     * @return 简历ID
     */
    String initResume(String userId);
}
