package ru.ithex.center.communication.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.ithex.baseweb.aspect.BaseControllerAspect;

@Aspect
@Component
public class ControllerAspect extends BaseControllerAspect {
    public ControllerAspect(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    @Pointcut("execution(* ru.ithex.center.communication.controller.*.*(..))")
    public void controllersPackagePointcut(){}
}
