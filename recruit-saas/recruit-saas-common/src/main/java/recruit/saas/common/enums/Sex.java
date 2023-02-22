package recruit.saas.common.enums;

/**
 * @author ZhangShenao
 * @date 2023/2/22 3:17 PM
 * Description 性别枚举
 */
public enum Sex {
    FEMALE(0, "女"),
    MALE(1, "男"),
    SECRET(2, "保密");
    int code;
    String name;

    Sex(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
