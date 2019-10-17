package ru.ithex.center.communication.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.ithex.baseweb.model.Validation;

import java.util.Arrays;

@Aspect
@Component
public class ControllerAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);
    private final ObjectMapper objectMapper;

    public ControllerAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Before("execution(* ru.ithex.center.communication.controller.*.*(..))")
    public void controllerAspect(JoinPoint joinPoint) {
        StringBuilder stringBuilder = new StringBuilder(String.format("REQUEST %s:\n", joinPoint.getSignature().toShortString()));
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] paramNames = codeSignature.getParameterNames();
        for (int paramNamesCounter = 0; paramNamesCounter < paramNames.length; paramNamesCounter++){
            stringBuilder.append(String.format("%s=%s\n", paramNames[paramNamesCounter], readArgValue(joinPoint.getArgs()[paramNamesCounter])));
        }
        LOGGER.info(stringBuilder.toString());
        Arrays.stream(joinPoint.getArgs()).filter(o -> o instanceof Validation).map(o -> (Validation) o).forEach(Validation::validate);
    }

    private String readArgValue(Object arg){
        String result = null;
        try{
            result = objectMapper.writeValueAsString(arg);
        }catch (JsonProcessingException e){
            result = "Couldn't read value";
        }
        return result;
    }

    @AfterReturning(
            pointcut = "execution(* ru.ithex.center.communication.controller.*.*(..))",
            returning = "result"
    )
    public void logReturnObject(Object result){
        LOGGER.info("RESPONSE\n{}", readArgValue(result));
    }

}
