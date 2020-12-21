package com.example.jpa.demo.web;

import com.example.jpa.demo.db.Address;
import com.example.jpa.demo.db.AddressRepository;
import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import com.example.jpa.demo.service.UserInfoService;
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
	private UserInfoRepository userInfoRepository;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private AddressRepository addressRepository;

	@GetMapping("/users")
	public List<UserInfo> getUserInfos() {
//		public List<UserInfo> getUserInfos(List<String> urls) {
		userInfoRepository.findByNameAndCreateTimeBetween("JK", Instant.now(),Instant.now());
		userInfoRepository.findByNameAndUrlIn("JK", Lists.newArrayList("http://www.baidu.com"));
		userInfoRepository.findByNameAndUrlIn("JK", Lists.newArrayList("1","2"));
		userInfoRepository.findByNameAndUrlIn("JK", Lists.newArrayList("1","2","3"));
		userInfoRepository.findByNameAndUrlIn("JK", Lists.newArrayList("1","2","3","3","3","3"));
//		userInfoRepository.findByNameAndUrlIn("jack",urls);
		userInfoRepository.findByName("jack");
		return userInfoRepository.findAll();
	}
	@GetMapping("/user/info/{id}")
//	@Cacheable(value = "userInfo", key = "{#root.methodName, #id}", unless = "#result == null") //利用默认key值生成规则value加key生成一个redis的key值，result==null的时候不进行缓存
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
//		userInfo.setAddressList(addresses);

		UserInfo userInfo1 = userInfoRepository.save(userInfo);
		addressRepository.saveAll(addresses);
		return userInfo1;
	}

}
