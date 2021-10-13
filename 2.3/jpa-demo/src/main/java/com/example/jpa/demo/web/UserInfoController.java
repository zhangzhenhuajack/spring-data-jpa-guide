package com.example.jpa.demo.web;

import com.example.jpa.demo.service.UserInfoService;
import com.example.jpa.demo.service.dto.UserInfoDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> 11899e64a2861c290c93fb0243556bd288c2f3d6


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

	//跟进UserId取用户的详细信息
	@GetMapping("/test2")
	public HttpEntity<String> test2() {
		System.out.println("test2:userId:begin");
		try {
			Thread.sleep(20*1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("test2:userId:end");
		return new HttpEntity<>("success");
	}
	//跟进UserId取用户的详细信息
	@GetMapping("/test")
	public HttpEntity<String> test(HttpServletResponse response) {
		System.out.println("userId");
		HttpEntity re = new HttpEntity<>("success");
//		response.setStatus(499);
		try {
			Thread.sleep(70*1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("userId:end");
		return re;
	}

}
