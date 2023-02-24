package recruit.saas.common.constants;

/**
 * @author ZhangShenao
 * @date 2023/2/24 12:11 PM
 * Description JWT相关常量定义
 */
public interface JWTConstants {
    String APP_TOKEN_PREFIX = "app";    //app端token前缀
    String SAAS_TOKEN_PREFIX = "saas";    //SaaS平台端token前缀
    String ADMIN_TOKEN_PREFIX = "admin";    //管理后台token前缀

    String TOKEN_PREFIX_DELIMITER = "@";    //token前缀分隔符
}
