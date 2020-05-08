package io.haechi.sample.server.config.support;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import io.haechi.sample.server.config.utils.ErrorHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggingAspect {
    private ILogger logger = SLoggerFactory.getLogger(LoggingAspect.class);
    private ErrorHelper errorHelper;

    public LoggingAspect(ErrorHelper errorHelper) {
        this.errorHelper = errorHelper;
    }

    @Pointcut("execution(* io.haechi..*Controller.*(..)) && !execution(* io.haechi.sample.server.web.GlobalExceptionHandler.*(..))")
    public void onRequest() {
    }

    @Around("onRequest()")
    public Object doRequestLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if(request == null) {
            logger.warn("LoggingHandler: RequestLogger's httpServletRequest is null!");
            return result;
        }
        String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = proceedingJoinPoint.getSignature().getName();

        logger.info(
                "RequestLogger",
                errorHelper.createErrorParams(
                        request,
                        "controller", controllerName,
                        "method", methodName
                )
        );

        return result;
    }
}
