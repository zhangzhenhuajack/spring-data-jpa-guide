package com.example.jpa.demo.web;

import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserInfoController {
	@Autowired
	private UserInfoRepository userInfoRepository;

	@GetMapping("/user/info/{id}")
	public UserInfo getUserInfoFromPath(@PathVariable("id") Long id) {
		return userInfoRepository.getOne(id);
	}

	@PostMapping("/user/info")
	public UserInfo saveUserInfo(@RequestBody UserInfo userInfo) {
		return userInfoRepository.save(userInfo);
	}
}
