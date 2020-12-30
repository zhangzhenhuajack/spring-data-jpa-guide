package com.example.jpa.demo.service;

import com.example.jpa.demo.service.dto.UserInfoDto;

public interface UserInfoService {
    UserInfoDto findByUserId(Long userId);
}
