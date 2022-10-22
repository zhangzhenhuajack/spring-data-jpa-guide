package com.example.id_generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserInfoController {
    @Autowired
    private UserInfoRespository userInfoRespository;
    @GetMapping("/test")
    public List<UserInfo> getUserInfo(){
        Long begin = System.currentTimeMillis();
        List<UserInfo> saveAll = new ArrayList<>();
        for (int i=0;i<2000;i++) {
            saveAll.add(new UserInfo("jack"+i,i));
            // id必须外面指定，不能通过mysql自动生成，否则batch就会失效
        }
        userInfoRespository.saveAll(saveAll);
        System.out.println(System.currentTimeMillis()-begin);
        return userInfoRespository.findAll();
    }
}
