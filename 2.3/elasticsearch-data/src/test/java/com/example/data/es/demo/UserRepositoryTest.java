package com.example.data.es.demo;

import com.example.data.es.demo.jpa.User;
import com.example.data.es.demo.jpa.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testJpa() {
        userRepository.save(User.builder().id(1L).name("jkdb").email("jack@email.com").build());

        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            System.out.println(user);
        });
    }
}
