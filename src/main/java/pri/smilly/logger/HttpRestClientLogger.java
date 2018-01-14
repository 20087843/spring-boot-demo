package pri.smilly.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import pri.smilly.restclient.HttpRestResult;
import pri.smilly.runtime.ProcessWrapper;

@Aspect
@Component
public class HttpRestClientLogger {

    @Pointcut("execution(* pri.smilly.restclient.HttpRestClient.execute(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {

    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void after(JoinPoint joinPoint, HttpRestResult result) {

    }

    @AfterThrowing(value = "pointcut()", throwing = "error")
    public void error(JoinPoint joinPoint, ProcessWrapper error) {

    }

}
