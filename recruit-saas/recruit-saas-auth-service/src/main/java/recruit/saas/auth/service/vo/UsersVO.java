package recruit.saas.auth.service.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import recruit.saas.auth.service.entity.Users;
import recruit.saas.common.enums.UsersShowWhichName;

/**
 * @author ZhangShenao
 * @date 2023/2/22 3:35 PM
 * Description 用户信息VO
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UsersVO {
    private String id;

    /**
     * 展示名
     */
    private String displayName;

    /**
     * 性别，1:男 0:女 2:保密
     */
    private Integer sex;

    /**
     * 用户头像
     */
    private String face;

    /**
     * 介绍
     */
    private String description;

    /**
     * 用户登录Token
     */
    private String token;

    public static UsersVO fromEntity(Users entity) {
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
