package com.example.jpa.demo.web;

import com.example.jpa.demo.db.Address;
import com.example.jpa.demo.db.AddressRepository;
import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import com.example.jpa.demo.service.UserInfoService;
import com.example.jpa.demo.service.dto.UserInfoDto;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@RestController()
@Log4j2
public class UserInfoController {
	@Autowired
	private UserInfoService userInfoService;

	//跟进UserId取用户的详细信息
	@GetMapping("/user/{userId}")
	public UserInfoDto findByUserId(@PathVariable Long userId) {
		System.out.println(userId);
		return userInfoService.findByUserId(userId);
	}


}
