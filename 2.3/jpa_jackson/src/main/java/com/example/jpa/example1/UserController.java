package com.example.jpa.example1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAddressRepository userAddressRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("user")
    public String saveUser() {
        System.out.println(objectMapper);
        User user = User.builder().name("jackxx").email("123456@126.com").build();
        UserAddress userAddress = UserAddress.builder().address("shanghai1").user(user).build();
        UserAddress userAddress2 = UserAddress.builder().address("shanghai2").user(user).build();
        userAddressRepository.saveAll(Lists.newArrayList(userAddress,userAddress2));
        return "success";
    }
    @GetMapping("user")
    public User loadUser(Long id) {
        System.out.println(objectMapper);
        return userRepository.getOne(id);
//        User user =  userRepository.findById(id).get();
//        user.getAddress().get(0).getAddress();
//        return user;
    }
    @GetMapping("users")
    public List<User> loadUsers() {
        return userRepository.findAll();
    }
}
