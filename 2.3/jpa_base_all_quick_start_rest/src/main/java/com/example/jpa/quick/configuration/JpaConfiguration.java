package com.example.jpa.quick.configuration;

import com.example.jpa.quick.core.MyAuditorAware;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfiguration {
	@Bean
	@ConditionalOnMissingBean(name = "myAuditorAware")
	MyAuditorAware myAuditorAware() {
		return new MyAuditorAware();
	}
}
