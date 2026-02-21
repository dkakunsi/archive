package com.unitedvision.sangihe.monev.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.sangihe.monev.util.EntityRestMessage;
import com.unitedvision.sangihe.monev.util.ListEntityRestMessage;
import com.unitedvision.sangihe.monev.util.RestMessage;

@Aspect
@Component
public class MessageConverter {
	
	@Around("execution(public com.unitedvision.sangihe.monev.util.RestMessage com.unitedvision.sangihe.monev.controller.*.*(..) throws com.unitedvision.sangihe.monev.exception.ApplicationException, javax.persistence.PersistenceException)")
	public @ResponseBody RestMessage process(ProceedingJoinPoint jointPoint) {
		try {
			return (RestMessage) jointPoint.proceed();
		} catch (Throwable e) {
			return RestMessage.error(new Exception(e.getMessage()));
		}
	}

	@Around("execution(public com.unitedvision.sangihe.monev.util.EntityRestMessage com.unitedvision.sangihe.monev.controller.*.*(..) throws com.unitedvision.sangihe.monev.exception.ApplicationException)")
	public @ResponseBody EntityRestMessage<?> getEntity(ProceedingJoinPoint jointPoint) {
		try {
			return (EntityRestMessage<?>) jointPoint.proceed();
		} catch (Throwable e) {
			return EntityRestMessage.entityError(new Exception(e.getMessage()));
		}
	}
	
	@Around("execution(public com.unitedvision.sangihe.monev.util.ListEntityRestMessage com.unitedvision.sangihe.monev.controller.*.*(..) throws com.unitedvision.sangihe.monev.exception.ApplicationException)")
	public @ResponseBody ListEntityRestMessage<?> getListEntity(ProceedingJoinPoint jointPoint) {
		try {
			return (ListEntityRestMessage<?>) jointPoint.proceed();
		} catch (Throwable e) {
			return ListEntityRestMessage.listEntityError(new Exception(e.getMessage()));
		}
	}
}
