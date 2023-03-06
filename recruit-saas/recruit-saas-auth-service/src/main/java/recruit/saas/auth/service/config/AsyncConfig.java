package recruit.saas.auth.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @author ZhangShenao
 * @date 2023/3/5 12:48 PM
 * Description 异步处理配置
 */
@Configuration
@Slf4j
public class AsyncConfig implements AsyncConfigurer {
    //自定义异常处理器
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            log.error("Async Exec Error! thread: {}, method: {}, params: {},", Thread.currentThread().getName(),
                    method.getName(),
                    params,
                    ex);
        };
    }

    //自定义短信线程池
    @Bean
    public Executor smsThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("SMS-Thread-Pool");
        executor.initialize();  //线程池初始化
        return executor;
    }
}
