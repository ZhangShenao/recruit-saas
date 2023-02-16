package recruit.saas.service.impl;

import recruit.saas.entity.ChatMessage;
import recruit.saas.mapper.ChatMessageMapper;
import recruit.saas.service.ChatMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天信息存储表 服务实现类
 * </p>
 *
 * @author ZhangShenao
 * @since 2023-02-16
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {

}
