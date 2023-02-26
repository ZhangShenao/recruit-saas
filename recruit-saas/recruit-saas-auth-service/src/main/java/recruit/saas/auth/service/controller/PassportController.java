package recruit.saas.auth.service.controller;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recruit.saas.auth.service.assembler.UsersAssembler;
import recruit.saas.auth.service.component.JWTGenerator;
import recruit.saas.auth.service.entity.Users;
import recruit.saas.auth.service.param.LogoutParam;
import recruit.saas.auth.service.param.MobileLoginParam;
import recruit.saas.auth.service.sevice.UsersService;
import recruit.saas.common.vo.UsersVO;
import recruit.saas.common.enums.JWTPlatformType;
import recruit.saas.common.rest.CommonRestResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static recruit.saas.common.enums.RedisKeys.SMS_CODE;
import static recruit.saas.common.rest.CommonResultCode.SMS_CODE_ERROR;

/**
 * @author ZhangShenao
 * @date 2023/2/22 2:22 PM
 * Description 登录与注册API
 */
@RestController
@RequestMapping("/passport")
public class PassportController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UsersService usersService;

    @Resource
    private JWTGenerator jwtGenerator;

    @Resource
    private UsersAssembler usersAssembler;

    //手机号验证码登录
    @PostMapping("/login_by_mobile")
    public CommonRestResponse<?> loginByMobile(@Valid @RequestBody MobileLoginParam param, HttpServletResponse response) {
        String mobile = param.getMobile();
        String code = param.getCode();

        //校验验证码是否合法
        String key = String.format(SMS_CODE.getKey(), mobile);
        String existedCode = stringRedisTemplate.opsForValue().get(key);
        if (!StringUtils.equalsIgnoreCase(code, existedCode)) {
            return CommonRestResponse.ofResultCode(SMS_CODE_ERROR);
        }

        //根据手机号查询用户,判断用户是否已经注册
        Optional<Users> existed = usersService.queryByMobile(mobile);

        //如果用户不存在,则根据手机号自动注册
        Users users = existed.orElseGet(() -> usersService.createByMobile(mobile));

        //登录成功,返回用户信息
        UsersVO vo = usersAssembler.usersEntity2VO(users);

        //生成JWT Token,并写入Response Header
        String payload = new Gson().toJson(vo);
        String token = jwtGenerator.generateTokenWithExpirationAndPrefix(payload, JWTPlatformType.APP.getTokenPrefix(), 60L * 60, TimeUnit.SECONDS);
        response.addHeader(JWTPlatformType.TOKEN_HEADER_KEY, token);
        return CommonRestResponse.success(vo);
    }

    //退出登录
    @PostMapping("/logout")
    public CommonRestResponse<?> logout(@Valid @RequestBody LogoutParam param) {
        //清除Redis中的token信息
//        String userId = param.getUserId();
//        String tokenKey = String.format(RedisKeys.USER_JWT_TOKEN.getKey(), userId);
//        stringRedisTemplate.delete(tokenKey);
        //TODO impl
        return CommonRestResponse.success();
    }
}
