package com.example.jpa.demo.service;

import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import com.example.jpa.demo.service.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

	/**
	 * 我们把逻辑封装在service方法里面，方法名字语义要清晰，就是说这个方法我们会取UserInfo的信息和Address的信息
	 *
	 * @param id
	 */
	@Override
	@Transactional
	public UserInfoDto getUserInfoAndAddress(Long id) {
		UserInfo u1 =  userInfoRepository.findById(id).get();
		return UserInfoDto.builder().name(u1.getName()).addressList(u1.getAddressList()).build();//按照业务要求，需要什么返回什么就可以了，让实体在service层之外是不可见的
	}
}
