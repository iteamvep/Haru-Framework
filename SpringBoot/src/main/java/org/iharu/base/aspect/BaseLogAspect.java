package org.iharu.base.aspect;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.iharu.aspect.AspectInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BaseLogAspect
  implements AspectInterface
{
  private static final Logger LOG = LoggerFactory.getLogger(BaseLogAspect.class);
  
  @Pointcut("execution(* org.iharu.exception.*.*(..))"
          + "or execution(* org.iharu.cache..*.*(..))"
          + "or execution(* org.iharu.util..*.*(..))"
          + "or execution(* org.iharu.database..*.*(..))"
          + "or execution(* org.iharu.httpclient..*.*(..))"
          + "or execution(* org.iharu.web..*.*(..))"
          + "or execution(* org.iharu.websocket..*.*(..))")
  public void pointCut() {}
  
  public void doBefore(JoinPoint joinPoint)
  {
    System.out.println("AOP Before Advice...");
  }
  
  public void doAfter(JoinPoint joinPoint)
  {
    System.out.println("AOP After Advice...");
  }
  
  public void afterReturn(JoinPoint joinPoint, Object returnVal)
  {
    System.out.println("AOP AfterReturning Advice:" + returnVal);
  }
  
  @AfterThrowing(pointcut="pointCut()", throwing="error")
  public void afterThrowing(JoinPoint joinPoint, Throwable error)
  {
    LOG.error("[Aspect-LogActions] Method Signature:{}", joinPoint.getSignature());
    LOG.error("[Aspect-LogActions] Exception:{}", ExceptionUtils.getStackTrace(error));
  }
  
  public Object around(ProceedingJoinPoint pjp)
  {
    System.out.println("AOP Aronud before...");
    Object result = null;
    try
    {
      result = pjp.proceed();
    }
    catch (Throwable e)
    {
      LOG.error("[Aspect-LogActions] Method Signature:{}", pjp.getSignature());
      LOG.error("[Aspect-LogActions] Exception:{}", ExceptionUtils.getStackTrace(e));
    }
    System.out.println("AOP Aronud after...");
    return result;
  }
}
