package recruit.saas.service.impl;

import recruit.saas.entity.JobType;
import recruit.saas.mapper.JobTypeMapper;
import recruit.saas.service.JobTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职位类别 服务实现类
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-03-02
 */
@Service
public class JobTypeServiceImpl extends ServiceImpl<JobTypeMapper, JobType> implements JobTypeService {

}
