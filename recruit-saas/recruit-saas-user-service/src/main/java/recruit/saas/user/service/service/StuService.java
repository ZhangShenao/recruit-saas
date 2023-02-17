package recruit.saas.user.service.service;

import recruit.saas.user.service.entity.Stu;

/**
 * @author ZhangShenao
 * @date 2023/2/17 11:14 AM
 * Description Stu服务
 */
public interface StuService {
    /**
     * 创建Stu
     * @param stu Stu实体
     * @return 是否创建成功
     */
    boolean create(Stu stu);
}
