package com.utkuertunc.async.aspects;

import com.utkuertunc.async.service.GitHubLookupService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class AspectLog {

    private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class);

    private final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    Method method;

    {
        try {
            method = GitHubLookupService.class.getDeclaredMethod("findUser", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Pointcut("@annotation(com.utkuertunc.async.aspects.CustomLogger)")
    public void logPointCut(){
    }

    @Before("logPointCut()")
    public void logBefore(JoinPoint joinPoint){

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CustomLogger ann = method.getAnnotation(CustomLogger.class);
        String paramName = ann.ignoreparam();

        if (paramName.contains("user")) {
            logger.info("[{}]Ignored Parameter Name", timestamp);
        }
        else {
            logger.info("[{}]Enter to {} with {} parameter", timestamp, signature.getName(),signature.getParameterNames());
        }
    }

    @AfterReturning("logPointCut()")
    public void logAfterReturning(JoinPoint joinPoint){

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CustomLogger ann = method.getAnnotation(CustomLogger.class);
        boolean booleanValue = ann.ignorereturn();
        if (!booleanValue){
            logger.info("[{}] Exit from to {} with return {}", timestamp, signature.getName(),signature.getMethod().getGenericReturnType());
        }
        else{
            logger.info("[{}]Ignored Return Type", timestamp);
        }
    }

}
