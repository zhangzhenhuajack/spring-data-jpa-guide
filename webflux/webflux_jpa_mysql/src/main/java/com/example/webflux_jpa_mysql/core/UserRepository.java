package com.example.webflux_jpa_mysql.core;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends R2dbcRepository<UserEntity,Long> {
    Flux<UserEntity> findByName(String name);
}
