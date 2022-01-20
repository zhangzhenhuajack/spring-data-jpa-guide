package com.example.webflux_jpa_mysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class WebfluxJpaMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxJpaMysqlApplication.class, args);
	}

}
