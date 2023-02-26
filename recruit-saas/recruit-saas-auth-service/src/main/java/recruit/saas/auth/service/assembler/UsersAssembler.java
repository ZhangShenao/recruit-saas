package recruit.saas.auth.service.assembler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import recruit.saas.auth.service.entity.Users;
import recruit.saas.common.enums.UsersShowWhichName;
import recruit.saas.common.vo.UsersVO;

/**
 * @author ZhangShenao
 * @date 2023/2/25 5:46 PM
 * Description 用户信息装配器
 */
@Component
@Slf4j
public class UsersAssembler {
    /**
     * 用户Entity->VO
     */
    public UsersVO usersEntity2VO(Users entity) {
        UsersVO vo = new UsersVO();
        vo.setId(entity.getId());
        if (UsersShowWhichName.REAL_NAME.getCode() == entity.getShowWhichName()) {
            vo.setDisplayName(entity.getRealName());
        } else {
            vo.setDisplayName(entity.getNickname());
        }
        vo.setSex(entity.getSex());
        vo.setFace(entity.getFace());
        vo.setDescription(entity.getDescription());
        return vo;
    }
}

