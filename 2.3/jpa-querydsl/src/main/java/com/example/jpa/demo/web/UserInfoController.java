package com.example.jpa.demo.web;

import com.example.jpa.demo.core.UserInfoQuerydslBinderCustomer;
import com.example.jpa.demo.db.QUserInfo;
import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Log4j2
public class UserInfoController {
	@Autowired
	private UserInfoRepository userInfoRepository;

	/**
	 * 根据参数动态的组合成Predicate查询条件
	 * @param predicate
	 * @param pageable
	 * @return
	 */
	@GetMapping("users/query/dsl1")
	public Page<UserInfo> query1(@QuerydslPredicate(root = UserInfo.class) Predicate predicate, Pageable pageable) {
		return userInfoRepository.findAll(predicate, pageable);
	}


	@GetMapping("users/query/dsl2")
	public Page<UserInfo> query2(@QuerydslPredicate(root = UserInfo.class) Predicate predicate, Pageable pageable) {
		return userInfoRepository.findAll(predicate, pageable);
	}

	@GetMapping("users/query/dsl3")
	public Page<UserInfo> query3(@QuerydslPredicate(root = UserInfo.class, bindings = UserInfoQuerydslBinderCustomer.class) Predicate predicate, Pageable pageable) {
		return userInfoRepository.findAll(predicate, pageable);
	}

	@PostMapping("users")
	public UserInfo save(@RequestBody UserInfo userInfo) {
		return userInfoRepository.save(userInfo);
	}

	@GetMapping("users/all")
	public Iterable<UserInfo> find() {
		QUserInfo user = QUserInfo.userInfo;
		//直接引用QUser通过下面的操作直接做查询
		Predicate predicate = user.name.startsWith("jack")
				.and(user.lastName.startsWithIgnoreCase("jack"));
		//分页排序
		Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC,"id"));
		PageRequest pageRequest = PageRequest.of(0,10,sort);
		return userInfoRepository.findAll(predicate,pageRequest);
	}

}
