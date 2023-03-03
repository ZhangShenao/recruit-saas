package recruit.saas.common.filter;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import recruit.saas.common.context.CurrentContext;
import recruit.saas.common.enums.JWTPlatformType;
import recruit.saas.common.vo.AdminVO;
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
        //从Request Header中解析登录用户和管理员信息,并保存至上下文
        parseCurrentUser(request, JWTPlatformType.APP);
        parseCurrentUser(request, JWTPlatformType.ADMIN);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //请求处理完成,清空上下文
        CurrentContext.clear();
    }

    private void parseCurrentUser(HttpServletRequest request, JWTPlatformType platformType) {
        try {
            String encoded = request.getHeader(platformType.getUserHeaderKey());
            if (StringUtils.isBlank(encoded)) {
                return;
            }
            String payload = URLDecoder.decode(encoded, StandardCharsets.UTF_8.name());
            if (StringUtils.isBlank(payload)) {
                return;
            }

            //APP/SaaS端
            if (JWTPlatformType.APP == platformType || JWTPlatformType.SAAS == platformType) {
                UsersVO usersVO = new Gson().fromJson(payload, UsersVO.class);
                if (usersVO != null) {
                    CurrentContext.setCurrentUser(usersVO);
                }
                return;
            }

            //Admin端
            if (JWTPlatformType.ADMIN == platformType) {
                AdminVO adminVO = new Gson().fromJson(payload, AdminVO.class);
                if (adminVO != null) {
                    CurrentContext.setCurrentAdminUser(adminVO);
                }
            }

        } catch (Exception e) {
            log.error("Parse Current User Error! platformType: {}", platformType, e);
        }
    }
}
