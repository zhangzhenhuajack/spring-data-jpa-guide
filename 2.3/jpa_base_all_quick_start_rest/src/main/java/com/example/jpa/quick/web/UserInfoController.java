package com.example.jpa.quick.web;

import com.example.jpa.quick.entity.UserInfo;
import com.example.jpa.quick.service.UserInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


/**
 * @author jack
 * 也可以自由自定义增删改查方法
 */
@RestController
public class UserInfoController {
    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/users")
    public Page<UserInfo> getUserInfos(PageRequest pageRequest) {
        return userInfoService.findAll(pageRequest);
    }
    @GetMapping("/user/{id}")
    public UserInfo getUserInfo(@PathVariable("id") Long id) {
        return userInfoService.findById(id).get();
    }

    @PostMapping("/user")
    public UserInfo saveUserInfo(@RequestBody UserInfo userInfo) {
        return userInfoService.save(userInfo);
    }
}
