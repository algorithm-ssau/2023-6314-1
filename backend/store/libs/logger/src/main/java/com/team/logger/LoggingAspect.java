package com.team.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Aspect
@Slf4j
public class LoggingAspect {
  /**
   * Pointcut that matches all repositories, services, clients and Web REST endpoints.
   */
  @Pointcut("within(@org.springframework.stereotype.Repository *)" +
    " || within(@org.springframework.stereotype.Service *)" +
    " || within(@org.springframework.web.bind.annotation.RestController *)" +
    " || within(@com.team.logger.stereotype.Client *)")
  public void springBeanPointcut() {}

  /**
   * Advice that logs methods throwing exceptions.
   *
   * @param joinPoint join point for advice
   * @param e exception
   */
  @AfterThrowing(pointcut = "springBeanPointcut()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
      joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
  }

  /**
   * Advice that logs when a method is entered and exited.
   *
   * @param joinPoint join point for advice
   * @return result
   * @throws Throwable throws IllegalArgumentException
   */
  @Around("springBeanPointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    if (log.isDebugEnabled()) {
      log.debug("Enter: {}.{}() with argument[s] = {}",
        joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName(),
        cutLarge(Arrays.toString(joinPoint.getArgs()))
      );
    }
    try {
      Object result = joinPoint.proceed();
      if (log.isDebugEnabled()) {
        log.debug("Exit: {}.{}() with result = {}",
          joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(),
          cutLarge(String.valueOf(result))
        );
      }
      return result;
    } catch (IllegalArgumentException e) {
      log.error("Illegal argument: {} in {}.{}()",
        cutLarge(Arrays.toString(joinPoint.getArgs())),
        joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName()
      );
      throw e;
    }
  }

  private String cutLarge(String toString) {
    return toString.length() >= 400 ? toString.substring(0, 400) + "..." : toString;
  }
}
