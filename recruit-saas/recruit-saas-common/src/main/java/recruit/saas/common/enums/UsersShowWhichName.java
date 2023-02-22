package recruit.saas.common.enums;

/**
 * @author ZhangShenao
 * @date 2023/2/22 3:16 PM
 * Description 用户名对外展示类型枚举
 */

public enum UsersShowWhichName {
    REAL_NAME(1, "展示真实姓名"),
    NICKNAME(2, "展示昵称");
    int code;
    String desc;

    UsersShowWhichName(int code, String desc) {
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
