package recruit.saas.common.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

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
     * 身份角色，1: 求职者，2: 求职者可以切换为HR进行招聘，同时拥有两个身份
     */
    private Integer role;

    /**
     * JWT登录Token
     */
    private String token;
}
