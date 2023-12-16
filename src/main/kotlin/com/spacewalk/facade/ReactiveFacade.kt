package com.spacewalk.facade

import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

object ReactiveFacade {
    fun <T> wrapInMono(serviceCall: () -> T): Mono<T> {
        return Mono.fromCallable(serviceCall)
            .subscribeOn(Schedulers.boundedElastic())
    }
}