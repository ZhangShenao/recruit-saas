package recruit.saas.user.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import recruit.saas.common.exception.CommonBusinessException;
import recruit.saas.common.utils.MD5Utils;
import recruit.saas.user.service.common.CommonPageResponse;
import recruit.saas.common.rest.CommonResultCode;
import recruit.saas.common.vo.AdminVO;
import recruit.saas.user.service.assembler.AdminAssembler;
import recruit.saas.user.service.entity.Admin;
import recruit.saas.user.service.mapper.AdminMapper;
import recruit.saas.user.service.service.AdminService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ZhangShenao
 * @date 2023/3/3 10:52 AM
 * Description 管理员服务实现类
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Resource
    private AdminAssembler adminAssembler;

    @Override
    @Transactional
    public boolean create(Admin entity) {
        //校验用户名的唯一性
        Optional<Admin> existed = queryByUsername(entity.getUsername());
        if (existed.isPresent()) {
            throw CommonBusinessException.ofResultCode(CommonResultCode.ADMIN_USERNAME_ALREADY_EXISTED);
        }

        int row = adminMapper.insert(entity);
        return (row == 1);
    }

    @Override
    public Optional<Admin> queryByUsername(String username) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, username);
        return Optional.ofNullable(adminMapper.selectOne(wrapper));
    }

    @Override
    public CommonPageResponse<AdminVO> listByPage(int since, int limit, String username) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            wrapper.eq(Admin::getUsername, username);
        }
        List<Admin> all = adminMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(all)) {
            return CommonPageResponse.empty(limit);
        }

        List<AdminVO> records = all.stream()
                .skip(since)
                .limit(limit)
                .map(x -> adminAssembler.entity2VO(x))
                .collect(Collectors.toList());
        return CommonPageResponse.valueOf(since, limit, all.size(), records);
    }

    @Override
    @Transactional
    public boolean deleteById(String id) {
        int deleted = adminMapper.deleteById(id);
        return (deleted > 0);
    }

    @Transactional
    @Override
    public boolean resetPassword(String id, String originPassword, String newPassword, String affirmNewPassword) {
        //输入密码校验
        if (StringUtils.equalsIgnoreCase(originPassword, newPassword)) {
            throw CommonBusinessException.ofResultCode(CommonResultCode.PASSWORD_MUST_BE_DIFFERENT);
        }
        if (!StringUtils.equalsIgnoreCase(newPassword, affirmNewPassword)) {
            throw CommonBusinessException.ofResultCode(CommonResultCode.PASSWORD_NOT_SAME);
        }
        Admin entity = adminMapper.selectById(id);
        if (entity == null) {
            throw CommonBusinessException.ofResultCode(CommonResultCode.ADMIN_NOT_EXISTS);
        }

        //校验原始密码
        String encrypted = MD5Utils.encrypt(originPassword, entity.getSalt());
        String password = entity.getPassword();
        if (!StringUtils.equalsIgnoreCase(encrypted, password)) {
            throw CommonBusinessException.ofResultCode(CommonResultCode.ORIGIN_PASSWORD_ERROR);
        }

        //生成随机盐,创建新的加密密码
        String salt = RandomStringUtils.randomAlphanumeric(6);
        entity.setSalt(salt);
        String newEncrypted = MD5Utils.encrypt(newPassword, salt);
        entity.setPassword(newEncrypted);

        int row = adminMapper.updateById(entity);
        return (row > 0);
    }
}
