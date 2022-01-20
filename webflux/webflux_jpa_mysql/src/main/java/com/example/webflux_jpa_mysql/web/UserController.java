package com.example.webflux_jpa_mysql.web;


import com.example.webflux_jpa_mysql.core.UserEntity;
import com.example.webflux_jpa_mysql.core.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/user/lists")
    public Flux<UserEntity> query() {
        return userRepository.findAll().doOnNext(System.out::println);
    }
    @GetMapping(value = "/user/sse",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserEntity> query2() {
        return userRepository.findAll().doOnNext(System.out::println);
    }
    @GetMapping(value = "/user/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UserEntity> queryById(@PathVariable Long id) {
        return userRepository.findById(id);
    }
    @PostMapping("/user/save")
    public Mono<UserEntity> saveUser(@RequestBody UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
