package com.example.jpa.demo.service;

import com.example.jpa.demo.db.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserInfoService {
    List<UserInfo> getTop2UserInfo();
    Optional<UserInfo> loadOne(Long id);
}
