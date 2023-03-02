package recruit.saas.service.impl;

import recruit.saas.entity.Interview;
import recruit.saas.mapper.InterviewMapper;
import recruit.saas.service.InterviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 面试邀约表
本表为次表，可做冗余，可以用mongo或者es替代 服务实现类
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-03-02
 */
@Service
public class InterviewServiceImpl extends ServiceImpl<InterviewMapper, Interview> implements InterviewService {

}
