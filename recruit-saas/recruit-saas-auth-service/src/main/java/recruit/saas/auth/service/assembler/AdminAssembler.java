package recruit.saas.auth.service.assembler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import recruit.saas.auth.service.entity.Admin;
import recruit.saas.auth.service.vo.AdminVO;

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
}
