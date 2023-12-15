package com.spacewalk.facade

import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

object ReactiveFacade {
    fun <T> wrapInMono(callable: () -> T): Mono<T> {
        return Mono.fromCallable(callable).subscribeOn(Schedulers.boundedElastic())
    }
}