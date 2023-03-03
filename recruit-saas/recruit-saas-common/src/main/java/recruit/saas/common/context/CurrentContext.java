package recruit.saas.common.context;

import recruit.saas.common.vo.AdminVO;
import recruit.saas.common.vo.UsersVO;

import java.util.Optional;

/**
 * @author ZhangShenao
 * @date 2023/2/26 11:16 AM
 * Description 当前请求上下文
 */
public class CurrentContext {
    private static ThreadLocal<UsersVO> currentUser = new ThreadLocal<>();   //当前登录用户

    private static ThreadLocal<AdminVO> currentAdminUser = new ThreadLocal<>(); //当前登录的管理员用户

    /**
     * 设置当前登录用户信息
     */
    public static void setCurrentUser(UsersVO user) {
        currentUser.set(user);
    }

    /**
     * 设置当前登录管理员用户信息
     */
    public static void setCurrentAdminUser(AdminVO admin) {
        currentAdminUser.set(admin);
    }

    /**
     * 获取当前登录的用户信息
     */
    public static Optional<UsersVO> getCurrentUser() {
        return Optional.ofNullable(currentUser.get());
    }

    /**
     * 获取当前登录的管理员用户信息
     */
    public static Optional<AdminVO> getCurrentAdminUser() {
        return Optional.ofNullable(currentAdminUser.get());
    }

    /**
     * 清空当前登录上下文
     */
    public static void clear() {
        currentUser.remove();
        currentAdminUser.remove();
    }
}
