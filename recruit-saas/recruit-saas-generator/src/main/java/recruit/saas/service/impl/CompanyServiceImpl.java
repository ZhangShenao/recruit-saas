package recruit.saas.service.impl;

import recruit.saas.entity.Company;
import recruit.saas.mapper.CompanyMapper;
import recruit.saas.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业表 服务实现类
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-02-17
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

}
