package recruit.saas.service.impl;

import recruit.saas.entity.Orders;
import recruit.saas.mapper.OrdersMapper;
import recruit.saas.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-02-17
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
