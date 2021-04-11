package com.example.jpa.demo.web;

import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Log4j2
public class UserInfoController {
	@Autowired
	private UserInfoRepository userInfoRepository;

	@GetMapping("users/query/dsl")
	public Page<UserInfo> query(@QuerydslPredicate(root = UserInfo.class)Predicate predicate, Pageable pageable) {
		return userInfoRepository.findAll(predicate,pageable);
	}

	@PostMapping("users")
	public UserInfo save(UserInfo userInfo){
		return userInfoRepository.save(userInfo);
	}

}
