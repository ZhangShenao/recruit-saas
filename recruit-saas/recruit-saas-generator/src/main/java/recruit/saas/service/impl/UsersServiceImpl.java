package recruit.saas.service.impl;

import recruit.saas.entity.Users;
import recruit.saas.mapper.UsersMapper;
import recruit.saas.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-02-17
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
