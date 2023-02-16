package recruit.saas.service.impl;

import recruit.saas.entity.Article;
import recruit.saas.mapper.ArticleMapper;
import recruit.saas.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-02-16
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
