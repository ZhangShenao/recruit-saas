package recruit.saas.auth.service.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recruit.saas.auth.service.assembler.AdminAssembler;
import recruit.saas.auth.service.component.JWTGenerator;
import recruit.saas.auth.service.entity.Admin;
import recruit.saas.auth.service.param.AdminLoginParam;
import recruit.saas.auth.service.service.AdminService;
import recruit.saas.auth.service.vo.AdminVO;
import recruit.saas.common.enums.JWTPlatformType;
import recruit.saas.common.rest.CommonRestResponse;
import recruit.saas.common.rest.CommonResultCode;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static recruit.saas.common.enums.JWTPlatformType.*;
import static recruit.saas.common.rest.CommonResultCode.*;

/**
 * @author ZhangShenao
 * @date 2023/3/2 10:16 AM
 * Description 管理后台登录与注册API
 */
@RestController
@RequestMapping("/admin/passport")
@Slf4j
public class AdminPassportController {
    @Resource
    private AdminService adminService;

    @Resource
    private AdminAssembler adminAssembler;

    @Resource
    private JWTGenerator jwtGenerator;

    //用户名&密码登录
    @PostMapping("/login")
    public CommonRestResponse<?> login(@RequestBody @Valid AdminLoginParam param) {
        String username = param.getUsername();
        String password = param.getPassword();
        Optional<Admin> admin = adminService.loginByUsernameAndPassword(username, password);
        if (!admin.isPresent()) {
            return CommonRestResponse.ofResultCode(USERNAME_OR_PASSWORD_ERROR);
        }
        Admin entity = admin.get();
        AdminVO vo = adminAssembler.entity2VO(entity);

        //生成JWT Token
        String payload = new Gson().toJson(vo);
        String token = jwtGenerator.generateTokenWithExpirationAndPrefix(payload, ADMIN.getTokenPrefix(), 1L, TimeUnit.HOURS);
        vo.setToken(token);
        return CommonRestResponse.success(vo);
    }
}
