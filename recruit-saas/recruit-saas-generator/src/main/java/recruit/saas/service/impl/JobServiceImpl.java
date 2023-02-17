package recruit.saas.service.impl;

import recruit.saas.entity.Job;
import recruit.saas.mapper.JobMapper;
import recruit.saas.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * HR发布的职位表 服务实现类
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-02-17
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

}
