package itb.sdrank.web.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import itb.sdrank.web.SimpleResponse;

@Aspect
@Component
public class ExceptionInterceptor {

  @Around("execution(public * itb.sdrank.web.controller.*.*(..) throws itb.sdrank.exception.SDRankException)")
  public @ResponseBody SimpleResponse process(ProceedingJoinPoint jointPoint) {
    try {
      return (SimpleResponse) jointPoint.proceed();
    } catch (Throwable e) {
      return new SimpleResponse(String.format("ERROR: %s", e.getMessage()));
    }
  }
}
