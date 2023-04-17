package recruit.saas.work.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recruit.saas.work.service.entity.Resume;
import recruit.saas.work.service.mapper.ResumeMapper;
import recruit.saas.work.service.service.ResumeService;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author ZhangShenao
 * @date 2023/4/17 9:37 AM
 * Description 简历服务实现
 */
@Service
@Slf4j
public class ResumeServiceImpl implements ResumeService {
    @Resource
    private ResumeMapper resumeMapper;

    @Override
    @Transactional
    public String initResume(String userId) {
        LambdaQueryWrapper<Resume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Resume::getUserId, userId);
        Resume resume = resumeMapper.selectOne(wrapper);
        if (resume != null) {   //简历已存在,无需初始化
            log.warn("Resume Already Exists, Skip Init! userId: {}", userId);
            return resume.getId();
        }

        //初始化简历
        resume = new Resume();
        resume.setUserId(userId);
        resume.setAdvantage(StringUtils.EMPTY);
        resume.setAdvantageHtml(StringUtils.EMPTY);
        resume.setCredentials(StringUtils.EMPTY);
        resume.setSkills(StringUtils.EMPTY);
        resume.setStatus(StringUtils.EMPTY);
        LocalDateTime now = LocalDateTime.now();
        resume.setCreateTime(now);
        resume.setUpdatedTime(now);
        resumeMapper.insert(resume);
        return resume.getId();
    }
}
