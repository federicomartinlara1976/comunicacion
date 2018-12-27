package net.bounceme.chronos.comunicacion.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Esto se usa para evaluar pruebas de rendimiento.
 *
 * @author SE06436
 */
@Aspect
@Configuration
public class ServicesTimeTrackAspect {
	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(ServicesTimeTrackAspect.class);
	
	/** The start time. */
	private long startTime;
	
	/**
	 * Track before.
	 *
	 * @param joinPoint the join point
	 */
	@Before("execution(* net.bounceme.chronos.comunicacion.services.impl.*.*(..))")
	public void trackBefore(JoinPoint joinPoint) {
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Track after.
	 *
	 * @param joinPoint the join point
	 */
	@After("execution(* net.bounceme.chronos.comunicacion.services.impl.*.*(..))")
	public void trackAfter(JoinPoint joinPoint) {
		long endTime = System.currentTimeMillis();
		logger.debug("{} execution time: {} milliseconds", joinPoint.getSignature(), (endTime - startTime));
	}
}
