package recruit.saas.user.service.assembler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import recruit.saas.common.utils.MD5Utils;
import recruit.saas.common.vo.AdminVO;
import recruit.saas.user.service.entity.Admin;
import recruit.saas.user.service.param.AssignAdminParam;

import java.time.LocalDateTime;

/**
 * @author ZhangShenao
 * @date 2023/3/2 10:23 AM
 * Description 管理员信息装配器
 */
@Component
@Slf4j
public class AdminAssembler {
    public AdminVO entity2VO(Admin entity) {
        AdminVO vo = new AdminVO();
        vo.setId(entity.getId());
        vo.setUsername(entity.getUsername());
        vo.setRemark(entity.getRemark());
        vo.setFace(entity.getFace());
        return vo;
    }

    //分配管理员param -> entity
    public Admin assignParam2Entity(AssignAdminParam param) {
        Admin entity = new Admin();
        entity.setUsername(param.getUsername());

        //随机生成盐
        String salt = RandomStringUtils.randomAlphanumeric(6);
        entity.setSalt(salt);

        //保存加盐加密后的密文密码
        String password = MD5Utils.encrypt(param.getPassword(), salt);
        entity.setPassword(password);

        entity.setRemark(param.getRemark());
        entity.setFace(StringUtils.EMPTY);

        LocalDateTime now = LocalDateTime.now();
        entity.setCreateTime(now);
        entity.setUpdatedTime(now);
        return entity;
    }
}
