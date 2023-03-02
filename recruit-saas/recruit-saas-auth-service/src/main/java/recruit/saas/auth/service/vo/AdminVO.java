package recruit.saas.auth.service.vo;

import lombok.Data;

/**
 * @author ZhangShenao
 * @date 2023/3/2 10:20 AM
 * Description 管理员信息VO
 */
@Data
public class AdminVO {
    private String id;

    /**
     * 登录名
     */
    private String username;

    /**
     * 备注
     */
    private String remark;

    /**
     * 头像
     */
    private String face;

    /**
     * JWT登录Token
     */
    private String token;
}
