package com.spacewalk.facade

import com.spacewalk.domain.account.Account
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.util.*

object ReactiveFacade {

    fun <T> wrapInMono(serviceCall: () -> T): Mono<T> {
        return Mono.fromCallable(serviceCall)
            .subscribeOn(Schedulers.boundedElastic())
    }

}