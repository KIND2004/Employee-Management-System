package com.xrontech.web.domain.security.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.xrontech.web.domain")
@EnableJpaRepositories("com.xrontech.web.domain")
@EnableTransactionManagement
@EnableJpaAuditing
public class DomainConfig {
}
