package pri.smilly.demo.server.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import pri.smilly.demo.runtime.ProcessWrapper;

@Aspect
@Component
public class ProcessExecutorLogger {

    @Pointcut("execution(* pri.smilly.demo.runtime.ProcessExecutor.execute(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {

    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void after(JoinPoint joinPoint, ProcessWrapper result) {

    }

    @AfterThrowing(value = "pointcut()", throwing = "error")
    public void error(JoinPoint joinPoint, ProcessWrapper error) {

    }
}
