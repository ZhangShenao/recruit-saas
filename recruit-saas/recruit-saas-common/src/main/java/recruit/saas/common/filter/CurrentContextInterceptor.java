package recruit.saas.common.filter;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import recruit.saas.common.context.CurrentContext;
import recruit.saas.common.enums.JWTPlatformType;
import recruit.saas.common.vo.UsersVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author ZhangShenao
 * @date 2023/2/26 11:19 AM
 * Description 当前请求上下文拦截器,用于设置、获取上下文信息
 */
@Slf4j
public class CurrentContextInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //从Request Header中获取登录用户信息,并保存至上下文

        try {
            String encoded = request.getHeader(JWTPlatformType.APP.getUserHeaderKey());
            String payload = URLDecoder.decode(encoded, StandardCharsets.UTF_8.name());
            if (StringUtils.isBlank(payload)) {
                return true;
            }

            UsersVO usersVO = new Gson().fromJson(payload, UsersVO.class);
            if (usersVO != null) {
                CurrentContext.setCurrentUser(usersVO);
            }
            return true;
        } catch (Exception e) {
            log.error("Get Current User Error!", e);
            return true;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //请求处理完成,清空上下文
        CurrentContext.clear();
    }
}
