package com.spacewalk.domain.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Aspect
@Component
class WrapInMonoAspect {

    @Around("@annotation(WrapInMono)")
    fun around(joinPoint: ProceedingJoinPoint): Mono<*> {
        return try {
            val result = joinPoint.proceed() as? Mono<*>
            result?.subscribeOn(Schedulers.boundedElastic()) ?: Mono.error<Any?>(RuntimeException("Method must return Mono"))
        } catch (e: Throwable) {
            Mono.error<Any>(e)
        }
    }
}