package recruit.saas.auth.service.service;

import recruit.saas.auth.service.entity.Admin;

import java.util.Optional;

/**
 * @author ZhangShenao
 * @date 2023/3/2 10:24 AM
 * Description 管理员服务
 */
public interface AdminService {
    /**
     * 根据用户名和密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 如果登录成功, 则返回管理员信息；如果登录失败,则返回Optional.empty()
     */
    Optional<Admin> loginByUsernameAndPassword(String username, String password);

    /**
     * 根据用户名查询管理员信息
     *
     * @param username 用户名
     * @return 管理员信息
     */
    Optional<Admin> queryByUsername(String username);
}
