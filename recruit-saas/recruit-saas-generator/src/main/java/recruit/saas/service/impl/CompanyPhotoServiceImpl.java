package recruit.saas.service.impl;

import recruit.saas.entity.CompanyPhoto;
import recruit.saas.mapper.CompanyPhotoMapper;
import recruit.saas.service.CompanyPhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业相册表，本表只存企业上传的图片 服务实现类
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-03-02
 */
@Service
public class CompanyPhotoServiceImpl extends ServiceImpl<CompanyPhotoMapper, CompanyPhoto> implements CompanyPhotoService {

}
