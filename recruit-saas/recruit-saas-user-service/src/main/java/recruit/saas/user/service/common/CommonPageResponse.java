package recruit.saas.user.service.common;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author ZhangShenao
 * @date 2023/3/3 11:39 AM
 * Description 通用分页响应
 */
@Data
public class CommonPageResponse<T> {
    private int since;    //分页偏移量, -1表示没有更多
    private int limit;   //分页大小
    private long total;   //总记录数
    private List<T> records; //记录列表

    public static <T> CommonPageResponse<T> valueOf(int since, int limit, long total, List<T> records) {
        CommonPageResponse<T> resp = new CommonPageResponse<>();
        resp.setLimit(limit);
        resp.setTotal(total);
        resp.setRecords(records);
        if (CollectionUtils.isEmpty(records) || records.size() < limit || limit + since >= total) {
            resp.setSince(-1);
        } else {
            resp.setSince(since + records.size());
        }
        return resp;
    }

    public static <T> CommonPageResponse<T> empty(int limit) {
        CommonPageResponse<T> resp = new CommonPageResponse<>();
        resp.setSince(-1);
        resp.setLimit(limit);
        resp.setTotal(0L);
        resp.setRecords(Collections.emptyList());
        return resp;
    }


}
