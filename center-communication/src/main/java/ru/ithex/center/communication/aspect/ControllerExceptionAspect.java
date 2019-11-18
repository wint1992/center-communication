package ru.ithex.center.communication.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.ithex.baseweb.aspect.BaseControllerExceptionAspect;

@Component
@Aspect
public class ControllerExceptionAspect extends BaseControllerExceptionAspect {
    @Override
    @Pointcut("execution(* ru.ithex.center.communication.controller.advice.*.*(..))")
    public void controllersAdvicePackagePointcut(){}
}
