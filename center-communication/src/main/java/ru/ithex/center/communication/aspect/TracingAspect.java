package ru.ithex.center.communication.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.ithex.baseweb.aspect.BaseExecutionTracingAspect;

@Aspect
@Component
public class TracingAspect extends BaseExecutionTracingAspect {
    @Override
    @Pointcut("within(ru.ithex.center.communication..*)")
    protected void mainPackagePointcut() {}
}
