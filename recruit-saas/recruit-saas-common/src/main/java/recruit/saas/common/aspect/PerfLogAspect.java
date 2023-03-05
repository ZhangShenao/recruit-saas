package recruit.saas.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

/**
 * @author ZhangShenao
 * @date 2023/3/3 6:08 PM
 * Description 性能日志切面
 */
@Aspect
@Slf4j
public class PerfLogAspect {
    //打印性能日志
    @Around("execution(* recruit.saas.*.service.service.impl..*.*(..))")
    public Object printPerfLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String signature = String.format("%s#%s", joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName());

        //使用Spring的StopWatch秒表进行性能统计
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(signature);
        Object result = joinPoint.proceed();
        stopWatch.stop();
        StopWatch.TaskInfo task = stopWatch.getLastTaskInfo();
        log.info("Exec {}, cost: {}ms", task.getTaskName(), task.getTimeMillis());
        return result;
    }
}
