package recruit.saas.service.impl;

import recruit.saas.entity.Admin;
import recruit.saas.mapper.AdminMapper;
import recruit.saas.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 运营管理系统的admin账户表，仅登录，不提供注册 服务实现类
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-02-16
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
