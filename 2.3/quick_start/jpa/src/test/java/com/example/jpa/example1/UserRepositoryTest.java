package com.example.jpa.example1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAddressRepository userAddressRepository;
    private Date now = new Date();
    @BeforeAll
    @Rollback(false)
    @Transactional
    void init() {
        User user = User.builder()
                .name("jack")
                .email("123456@126.com")
                .sex(SexEnum.BOY)
                .age(20)
                .createDate(Instant.now())
                .updateDate(now)
//                .address(Lists.newArrayList(UserAddress.builder().address("shanghai").build(),UserAddress.builder().address("beijing").build()))
                .build();
//        userRepository.save(user);由于关联关系维护方在UserAddress所以，此处级联Insert没有用
        userAddressRepository.saveAll(Lists.newArrayList(UserAddress.builder().user(user).address("shanghai").build(),
                UserAddress.builder().user(user).address("beijing").build()));

    }
    @Test
    @Rollback(false)
    public void testQBE2() throws JsonProcessingException {
        User request = User.builder()
                .name("jack").age(20).email("12345")
//                .address(Lists.newArrayList(UserAddress.builder().address("shang").build()))
                .build();
        UserAddress address = UserAddress.builder().address("shang").user(request).build();

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("user.email", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("address", ExampleMatcher.GenericPropertyMatchers.startsWith());

        Page<UserAddress> u = userAddressRepository.findAll(Example.of(address,exampleMatcher), PageRequest.of(0,2));

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(u));
    }
    @Test
    @Rollback(false)
    public void testQBE() throws JsonProcessingException {
        User request = User.builder()
                .name("jack").age(20).email("12345").updateDate(now).sex(null)
                .address(Lists.newArrayList(UserAddress.builder().address("shang").build()))
                .build();

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("address.address", ExampleMatcher.GenericPropertyMatchers.startsWith());

        Page<User> u = userRepository.findAll(Example.of(request,exampleMatcher), PageRequest.of(0,2));

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(u));
    }
}
