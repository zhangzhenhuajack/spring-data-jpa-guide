package com.example.jpa.example1.web;

import com.example.jpa.example1.UserInfo;
import com.example.jpa.example1.UserInfoInterface;
import com.example.jpa.example1.UserInfoRepository;
import com.example.jpa.example1.common.MyPageRequest;
import com.example.jpa.example1.common.WarpWithData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserInfoController {
	@Autowired
	private UserInfoRepository userInfoRepository;

	@GetMapping("/user2/{id}")
	@WarpWithData
	public UserInfo getUserInfoFromPath(@PathVariable("id") Long id) {
		return userInfoRepository.getOne(id);
	}
	/**
	 * 从 request的param中的ID变量值，知己转化成UserInfo实体
	 * @param userInfo
	 * @return
	 */
	@GetMapping("/user")
	public UserInfo getUserInfoFromRequestParam(@RequestParam("id") UserInfo userInfo) {
		return userInfo;
	}
	//用Pageable这种方式也是可以的
	@GetMapping("/users")
	public Page<UserInfo> queryByPage(Pageable pageable, UserInfo userInfo) {
		return userInfoRepository.findAll(Example.of(userInfo),pageable);
	}

	//用MyPageRequest进行接收
	@GetMapping("/users/mypage")
	public Page<UserInfo> queryByMyPage(MyPageRequest pageable, UserInfo userInfo) {
		return userInfoRepository.findAll(Example.of(userInfo),pageable);
	}

	@GetMapping("/users/sort")
	public HttpEntity<List<UserInfo>> queryBySort(Sort sort, UserInfoInterface userInfoInterface) {
		System.out.println(userInfoInterface);
		return new HttpEntity<>(userInfoRepository.findAll(sort));
	}
	/**
	 * 测试Projected的支持
	 *
	 * @param userInfoInterface
	 * @return
	 */
	@PostMapping("/users/projected")
	public UserInfoInterface saveUserInfo(@RequestBody UserInfoInterface userInfoInterface) {
		return userInfoInterface;
	}

	/**
	 * @param userInfo
	 * @return
	 */
	@PostMapping("/user/info")
	public UserInfo saveUserInfo(@RequestBody UserInfo userInfo) {
		return userInfoRepository.save(userInfo);
	}

	@GetMapping(value = "user/dsl")
	Page<UserInfo> index(@QuerydslPredicate(root = UserInfo.class) com.querydsl.core.types.Predicate predicate, Pageable pageable) {
		return userInfoRepository.findAll(predicate, pageable);
	}

	//获得当前用户的信息
	@GetMapping("user/info")
	public UserInfo getUserInfo(UserInfo userInfo) {
		return userInfo;
	}
	//给当前用户 say hello
	@PostMapping("sayHello")
	public String sayHello(UserInfo userInfo) {
		return "hello " + userInfo.getTelephone();
	}
}
