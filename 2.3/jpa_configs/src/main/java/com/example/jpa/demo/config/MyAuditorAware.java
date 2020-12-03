package com.example.jpa.demo.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
public class MyAuditorAware implements AuditorAware<Integer> {
	@Override
	public Optional<Integer> getCurrentAuditor() {
		return Optional.ofNullable(1);
	}
}
