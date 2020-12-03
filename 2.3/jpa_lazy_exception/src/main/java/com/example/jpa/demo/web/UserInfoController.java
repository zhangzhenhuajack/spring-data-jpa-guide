package com.example.jpa.demo.web;

import com.example.jpa.demo.core.TransactionHelper;
import com.example.jpa.demo.db.Address;
import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import com.example.jpa.demo.service.UserInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@Log4j2
public class UserInfoController {
	private TransactionHelper transactionHelper;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private UserInfoService userInfoService;

	@GetMapping("/user/info/{id}")
	public UserInfo getUserInfoFromPath(@PathVariable("id") Long id) {
//		UserInfo u1 =  userInfoRepository.getOne(id);
		UserInfo u1 =  userInfoRepository.findById(id).get();
//		UserInfo u1 =  userInfoService.getUserInfoAndAddress(id);
//		System.out.println(u1.getAddressList().get(0).getCity());

//		UserInfo u2 = userInfoService.loadOne(id).orElse(null);
//		System.out.println(u2.getAddressList().get(0).getCity());
		return u1;
	}

	@PostMapping("/user/info")
	public UserInfo saveUserInfo(@RequestBody UserInfo userInfo) {
		List<com.example.jpa.demo.db.Address> addresses = new ArrayList<>();
		addresses.add(Address.builder().city("shanghai").userInfo(userInfo).build());
		addresses.add(Address.builder().city("jiangshu").userInfo(userInfo).build());
		userInfo.setAddressList(addresses);
		return userInfoRepository.save(userInfo);
	}

}
