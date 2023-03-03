package recruit.saas.user.service.service;

import recruit.saas.common.vo.AdminVO;
import recruit.saas.user.service.common.CommonPageResponse;
import recruit.saas.user.service.entity.Admin;

import java.util.Optional;

/**
 * @author ZhangShenao
 * @date 2023/3/3 10:51 AM
 * Description 管理员服务
 */
public interface AdminService {
    /**
     * 创建管理员
     *
     * @param entity 管理员实体类
     * @return 是否创建成功
     */
    boolean create(Admin entity);

    /**
     * 根据用户名查询管理员信息
     *
     * @param username 用户名
     * @return 管理员信息
     */
    Optional<Admin> queryByUsername(String username);

    /**
     * 分页查询管理员账号列表
     *
     * @param since    分页偏移量
     * @param limit    分页大小
     * @param username 用户名
     * @return 分页信息
     */
    CommonPageResponse<AdminVO> listByPage(int since, int limit, String username);

    /**
     * 根据ID删除
     *
     * @param id 管理员账号ID
     * @return 是否删除成功
     */
    boolean deleteById(String id);

    /**
     * 重置密码
     *
     * @param id                账号ID
     * @param originPassword    原始密码
     * @param newPassword       新密码
     * @param affirmNewPassword 确认新密码
     * @return 是否操作成功
     */
    boolean resetPassword(String id, String originPassword, String newPassword, String affirmNewPassword);
}
