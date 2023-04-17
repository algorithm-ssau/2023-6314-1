package com.team.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

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
      log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName(), toStringArgs(joinPoint.getArgs()));
    }
    try {
      Object result = joinPoint.proceed();
      if (log.isDebugEnabled()) {
        log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(), toStringArgs(result));
      }
      return result;
    } catch (IllegalArgumentException e) {
      log.error("Illegal argument: {} in {}.{}()", toStringArgs(joinPoint.getArgs()),
        joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
      throw e;
    }
  }

  private String toStringArgs(Object... args) {
    if (args.length == 0) return "[]";

    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < args.length - 1; i++) {
      sb.append(cutIfImage(args[i].toString())).append(", ");
    }
    sb.append(cutIfImage(String.valueOf(args[args.length - 1]))).append("]");
    return sb.toString();
  }

  private String cutIfImage(String arg) {
    String IMAGE_PREFIX = "data:image/png;base64,";
    if (arg.startsWith(IMAGE_PREFIX)) {
      arg = "image with length: " + arg.length();
    }
    return arg;
  }
}
