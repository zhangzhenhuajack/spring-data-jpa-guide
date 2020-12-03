package com.example.jpa;

import com.example.jpa.example1.customized.CustomerBaseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableJpaRepositories(repositoryImplementationPostfix = "Impl",repositoryBaseClass = CustomerBaseRepository.class)
public class JpaApplication {
	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

}
