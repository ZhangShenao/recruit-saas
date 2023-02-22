package recruit.saas.auth.service.sevice;

import recruit.saas.auth.service.entity.Users;

import java.util.Optional;

/**
 * @author ZhangShenao
 * @date 2023/2/22 2:30 PM
 * Description 用户服务接口
 */
public interface UsersService {
    /**
     * 根据手机号查询用户
     */
    Optional<Users> queryByMobile(String mobile);

    /**
     * 根据手机号创建用户
     */
    Users createByMobile(String mobile);
}
