package recruit.saas.common.enums;

/**
 * @author ZhangShenao
 * @date 2023/2/25 5:18 PM
 * Description JWT平台类型
 */
public enum JWTPlatformType {
    APP(1, "APP端", "app"),
    SAAS(2, "企业平台SaaS端", "saas"),
    ADMIN(3, "管理后台", "admin"),
    ;

    public static final String TOKEN_PREFIX_DELIMITER = "@";    //token前缀分隔符
    public static final String TOKEN_HEADER_KEY = "X-TOKEN";    //Token Header前缀

    private int code;
    private String name;
    private String tokenPrefix; //Token前缀

    JWTPlatformType(int code, String name, String tokenPrefix) {
        this.code = code;
        this.name = name;
        this.tokenPrefix = tokenPrefix;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }
}
