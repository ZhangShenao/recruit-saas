package recruit.saas.service.impl;

import recruit.saas.entity.SysParams;
import recruit.saas.mapper.SysParamsMapper;
import recruit.saas.service.SysParamsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统参数配置表，本表仅有一条记录 服务实现类
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-03-02
 */
@Service
public class SysParamsServiceImpl extends ServiceImpl<SysParamsMapper, SysParams> implements SysParamsService {

}
