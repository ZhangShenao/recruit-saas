package recruit.saas.user.service.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import recruit.saas.common.context.CurrentContext;
import recruit.saas.common.rest.CommonRestResponse;
import recruit.saas.common.vo.UsersVO;

import java.util.Optional;

/**
 * @author ZhangShenao
 * @date 2023/2/16 11:36 AM
 * Description
 */
@RestController
@Slf4j
public class HelloController {
    @Value("${PORT}")
    private int port;

    @GetMapping("/hello")
    public CommonRestResponse<String> hello() {
        //从当前请求上下文中获取登录用户信息
        StringBuilder greeting = new StringBuilder("Welcome to user service. port: ")
                .append(port)
                .append(".");
        Optional<UsersVO> currentUser = CurrentContext.getCurrentUser();
        currentUser.ifPresent(usersVO -> greeting.append("Current User: ").append(new Gson().toJson(usersVO)).append("."));
        return CommonRestResponse.success(greeting.toString());
    }
}
