package recruit.saas.user.service.param;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import recruit.saas.user.service.entity.Stu;

/**
 * @author ZhangShenao
 * @date 2023/2/17 11:21 AM
 * Description 创建Stu请求参数
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateStuParam {
    private String name;
    private int age;

    public Stu toEntity() {
        Stu entity = new Stu();
        entity.setName(name);
        entity.setAge(age);
        return entity;
    }
}
