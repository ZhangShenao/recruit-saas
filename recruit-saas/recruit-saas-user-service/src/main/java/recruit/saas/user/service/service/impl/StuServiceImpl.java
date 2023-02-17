package recruit.saas.user.service.service.impl;

import org.springframework.stereotype.Service;
import recruit.saas.user.service.entity.Stu;
import recruit.saas.user.service.mapper.StuMapper;
import recruit.saas.user.service.service.StuService;

import javax.annotation.Resource;

/**
 * @author ZhangShenao
 * @date 2023/2/17 11:15 AM
 * Description
 */
@Service
public class StuServiceImpl implements StuService {
    @Resource
    private StuMapper stuMapper;

    @Override
    public boolean create(Stu stu) {
        int row = stuMapper.insert(stu);
        return (row == 1);
    }
}
