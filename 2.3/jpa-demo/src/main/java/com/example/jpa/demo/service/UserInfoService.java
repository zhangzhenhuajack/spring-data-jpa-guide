package com.example.jpa.demo.service;

import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.service.dto.UserInfoDto;

import java.util.List;
import java.util.Optional;

public interface UserInfoService {
    List<UserInfo> getTop2UserInfo();
    Optional<UserInfo> loadOne(Long id);

    /**
     * 我们把逻辑封装在service方法里面，方法名字语义要清晰，就是说这个方法我们会取UserInfo的信息和Address的信息
     */
//    UserInfo getUserInfoAndAddress(Long id);
    UserInfoDto getUserInfoAndAddress(Long id);

    List<UserInfo> getAllUserWithAddress();
}
