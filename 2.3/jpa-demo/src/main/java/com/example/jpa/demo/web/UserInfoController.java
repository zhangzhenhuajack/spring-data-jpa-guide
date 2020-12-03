package com.example.jpa.demo.web;

import com.example.jpa.demo.db.Address;
import com.example.jpa.demo.db.AddressRepository;
import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import com.example.jpa.demo.service.UserInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@RestController
@Log4j2
public class UserInfoController {
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private AddressRepository addressRepository;

	@GetMapping("/users")
	public List<UserInfo> getUserInfos() {
		return userInfoRepository.findAll();
	}
	@GetMapping("/user/info/{id}")
	public UserInfo getUserInfo(@PathVariable("id") Long id) {
		return userInfoRepository.findById(id).get();
	}
	@GetMapping("/user/address/{id}")
	public Address getUserAddress(@PathVariable("id") Long id) {
		return addressRepository.findById(id).get();
	}
	@GetMapping("/user/addresses")
	public List<Address> getUserAddressList() {
		return addressRepository.findAll();
	}

	@PostMapping("/user/info")
	@Transactional
	public UserInfo saveUserInfo(@RequestBody UserInfo userInfo) {
		List<com.example.jpa.demo.db.Address> addresses = new ArrayList<>();
		addresses.add(Address.builder().city("shanghai").userInfo(userInfo).build());
		addresses.add(Address.builder().city("jiangshu").userInfo(userInfo).build());
		userInfo.setAddressList(addresses);

		UserInfo userInfo1 = userInfoRepository.save(userInfo);
		addressRepository.saveAll(addresses);
		return userInfo1;
	}

}
