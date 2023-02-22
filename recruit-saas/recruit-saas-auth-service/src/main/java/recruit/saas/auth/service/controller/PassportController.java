package recruit.saas.auth.service.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recruit.saas.auth.service.entity.Users;
import recruit.saas.auth.service.param.MobileLoginParam;
import recruit.saas.auth.service.sevice.UsersService;
import recruit.saas.auth.service.vo.UsersVo;
import recruit.saas.common.rest.CommonRestResponse;

import javax.annotation.Resource;
import javax.validation.Valid;


import java.util.Optional;

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

    //手机号验证码登录
    @PostMapping("/login_by_mobile")
    public CommonRestResponse<?> loginByMobile(@Valid @RequestBody MobileLoginParam param) {
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
        UsersVo vo = UsersVo.fromEntity(users);
        return CommonRestResponse.success(vo);
    }
}
