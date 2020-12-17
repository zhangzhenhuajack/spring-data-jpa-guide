package com.example.jpa.demo.service;

import com.example.jpa.demo.db.AddressRepository;
import com.example.jpa.demo.db.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private AddressRepository addressRepository;
}
