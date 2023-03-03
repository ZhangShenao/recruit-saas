package recruit.saas.common.rest;

/**
 * @author ZhangShenao
 * @date 2023/2/16 2:07 PM
 * Description 通用相应状态码
 */
public enum CommonResultCode {
    //通用状态码
    SUCCESS(200, true, "处理成功！"),
    FAILED(500, false, "处理失败！"),

    //通用异常
    INVALID_PARAM(1001, false, "请求参数不合法，请校验后重试~"),
    IP_DENIED(1002, false, "该IP已被封禁，请尝试其他IP~"),
    REQUEST_TOO_FREQUENT(1003, false, "访问过于频繁，请稍后再试~"),

    //短信相关
    SMS_SNT_TOO_FREQUENTLY(2001, false, "短信发送过于频繁，请稍后再试~"),
    SMS_CODE_ERROR(2002, false, "短信验证码异常，请重试~"),

    //登录相关
    NOT_LOGIN(3001, false, "请登录后再继续操作~"),
    TOKEN_EXPIRED(3002, false, "您的登录状态已过期，请重新登录~"),
    INVALID_TOKEN(3003, false, "您的登录状态异常，请重新登录~"),
    QR_TOKEN_EXPIRED(3004, false, "二维码已过期，请重新扫码登录~"),
    QR_TOKEN_INVALID(3005, false, "二维码状态异常，请重新扫码登录~"),
    ONLY_HR_CAN_LOGIN(3006, false, "该登录功能仅针对HR角色开放~"),
    USERNAME_OR_PASSWORD_ERROR(3007, false, "用户名或密码错误，请确认后重试~"),

    //管理后台相关
    ASSIGN_ADMIN_FAIL(4001, false, "分配管理员账号失败，请稍后重试~"),
    ADMIN_USERNAME_ALREADY_EXISTED(4002, false, "管理员用户名已经存在，请修改后重试~"),
    DELETE_ADMIN_FAIL(4003, false, "删除管理员账号失败，请稍后重试~"),
    PASSWORD_MUST_BE_DIFFERENT(4004, false, "新旧密码不能相同，请修改后重试~"),
    PASSWORD_NOT_SAME(4005, false, "两次输入密码不一致，请修改后重试~"),
    ORIGIN_PASSWORD_ERROR(4006, false, "原始密码错误，请修改后重试~"),
    ADMIN_NOT_EXISTS(4007, false, "管理员账号不存在，请确认后重试~"),
    RESET_PASSWORD_FAIL(4008, false, "重置管理员密码失败，请稍后重试~"),

    ;
    private int status; //状态码

    private boolean success;    // 调用是否成功

    private String msg;  // 响应消息，可以为成功或者失败的消息

    CommonResultCode(Integer status, Boolean success, String msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }
}
