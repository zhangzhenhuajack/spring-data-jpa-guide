package com.example.jpa.demo.service;

import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public List<UserInfo> getTop2UserInfo() {
		return userInfoRepository.findAll();
	}

	@Override
	public Optional<UserInfo> loadOne(Long id) {
		return userInfoRepository.findById(id);
	}
}
