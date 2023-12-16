package com.spacewalk.security.jwt

import org.slf4j.LoggerFactory
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class JwtAuthFilter(private val jwtTokenProvider: JwtTokenProvider) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val jwtToken = jwtTokenProvider.resolveToken(exchange.request)
        return if ((jwtToken != null) && jwtTokenProvider.validateToken(jwtToken)) {
            val authentication = jwtTokenProvider.getAuthentication(jwtToken)
            ReactiveSecurityContextHolder.withAuthentication(authentication)
            chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
        } else {
            chain.filter(exchange)
        }
    }


}