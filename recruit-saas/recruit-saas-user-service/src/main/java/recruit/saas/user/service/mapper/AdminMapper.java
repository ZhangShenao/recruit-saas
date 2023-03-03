package recruit.saas.user.service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import recruit.saas.user.service.entity.Admin;

/**
 * <p>
 * 运营管理系统的admin账户表，仅登录，不提供注册 Mapper 接口
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-03-02
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

}
