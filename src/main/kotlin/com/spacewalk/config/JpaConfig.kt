package com.spacewalk.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EntityScan(basePackages = ["com.spacework.domain"])
@EnableJpaRepositories(basePackages = ["com.spacewalk.domain"])
@EnableTransactionManagement
@EnableJpaAuditing
class JpaConfig {
}