package com.spacewalk.config

import com.spacewalk.security.jwt.JwtAuthFilter
import com.spacewalk.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf().disable()
            .formLogin().disable()
            .authorizeExchange()
            .pathMatchers("/api/auth/login").permitAll()
            .pathMatchers(HttpMethod.POST, "/api/users").permitAll()
            .pathMatchers("/api/users/**").hasRole("USER")
            .pathMatchers("/api/managers/**").hasRole("MANAGER")
            .pathMatchers("/api/admin/**").hasRole("ADMIN")
            .pathMatchers("/**").hasRole("USER")
            .anyExchange().authenticated()
            .and().addFilterAt(JwtAuthFilter(jwtTokenProvider()), SecurityWebFiltersOrder.AUTHENTICATION)
            .build()
    }

    @Bean
    fun jwtTokenProvider(): JwtTokenProvider {
        return JwtTokenProvider()
    }

    @Bean
    fun roleHierarchy() : RoleHierarchy {
        val roleHierarchy = RoleHierarchyImpl()
        val hierarchy = "ROLE_ADMIN > ROLE_MANAGER\nROLE_MANAGER > ROLE_USER"
        roleHierarchy.setHierarchy(hierarchy)
        return roleHierarchy
    }


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

}