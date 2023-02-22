package recruit.saas.common.enums;

/**
 * @author ZhangShenao
 * @date 2023/2/22 3:30 PM
 * Description 用户角色枚举
 */
public enum UsersRole {
    CANDIDATE(1, "求职者"),
    HR(2, "HR");

    int code;
    String desc;

    UsersRole(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
