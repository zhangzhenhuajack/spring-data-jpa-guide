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
	public UserInfo saveUserInfo(@RequestBody UserInfo userInfo) throws InterruptedException {
		UserInfo u2 = userInfoRepository.findById(1L).orElse(null);
		if (u2!=null) {
			u2.setLastName("jack"+userInfo.getLastModifiedTime());
			userInfoRepository.save(u2);
			System.out.println("模拟事务执行完之后耗时操作........");
			Thread.sleep(1000*60*2L);
			System.out.println("耗时操作执行完毕.......");
		}
		return userInfoRepository.save(userInfo);
	}
}
