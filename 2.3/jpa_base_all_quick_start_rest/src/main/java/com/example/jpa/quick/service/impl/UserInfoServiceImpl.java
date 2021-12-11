package com.example.jpa.quick.service.impl;

import com.example.jpa.quick.core.BaseServiceImpl;
import com.example.jpa.quick.dao.UserInfoRepository;
import com.example.jpa.quick.entity.UserInfo;
import com.example.jpa.quick.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author jack
 */
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo,Long, UserInfoRepository> implements UserInfoService {
	public UserInfoServiceImpl(UserInfoRepository repository) {
		super(repository);
	}
}
