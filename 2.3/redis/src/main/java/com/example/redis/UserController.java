package com.example.redis;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @GetMapping("user")
//    @Cacheable(cacheNames = "user2")
    public User getUser() {
        System.out.println("ddddd");
        return new User("abc","def",new Address("address"),null);
    }
    @GetMapping("users")
    @Cacheable(cacheNames = "user3")
    public List<User> getUsers() {
        System.out.println("ddddd");
        Map<String,Address> addressMap = new HashMap<>();
        addressMap.put("111",new Address("jack2"));
        List<User> users = new ArrayList<>();
        users.add(new User("abc","def",new Address("address"),null));
        users.add(new User("abc1","def1",null,addressMap));
        return users;
    }
}
