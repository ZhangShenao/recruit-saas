package recruit.saas.auth.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import recruit.saas.auth.service.entity.Admin;
import recruit.saas.auth.service.mapper.AdminMapper;
import recruit.saas.auth.service.service.AdminService;
import recruit.saas.common.utils.MD5Utils;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author ZhangShenao
 * @date 2023/3/2 10:27 AM
 * Description 管理员服务实现类
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Override
    public Optional<Admin> loginByUsernameAndPassword(String username, String password) {
        //根据用户名查询管理员
        Optional<Admin> admin = queryByUsername(username);
        if (!admin.isPresent()) {
            return Optional.empty();
        }

        //对用户输入的密码进行加盐,并与数据库中保存的密码进行比较,如果相同,则登录成功;否则登录失败
        String encryptedPassword = MD5Utils.encrypt(password, admin.get().getSalt());
        if (StringUtils.equalsIgnoreCase(admin.get().getPassword(), encryptedPassword)) {
            return admin;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Admin> queryByUsername(String username) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, username);
        return Optional.ofNullable(adminMapper.selectOne(wrapper));
    }
}
