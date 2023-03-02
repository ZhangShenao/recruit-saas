package recruit.saas.gateway.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhangShenao
 * @date 2023/3/1 8:12 PM
 * Description IP限流配置
 */
@Component
@RefreshScope
@Data
public class IPLimitProperties {
    @Value("${ip.limit.include-urls}")
    private List<String> includeUrls; //需要校验的URL列表

    @Value("${ip.limit.black-list}")
    private List<String> blackList; //IP黑名单列表

    @Value("${ip.limit.limit-count}")
    private int limitCount;    //访问次数限制

    @Value("${ip.limit.time-interval-sec}")
    private int timeIntervalSec;   //统计时间间隔（s）

    @Value("${ip.limit.deny-sec}")
    private int denySec;    //拒绝访问时间（s）
}
