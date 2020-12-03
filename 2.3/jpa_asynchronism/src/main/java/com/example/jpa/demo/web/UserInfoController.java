package com.example.jpa.demo.web;

import com.example.jpa.demo.core.TransactionHelper;
import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import com.example.jpa.demo.service.UserInfoService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


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

	@Autowired
	private  Executor executor;

	/**
	 * 模拟一个业务service方法，里面有一些异步操作，一些业务方法里面可能修改了两次用户信息
	 * @param name
	 * @return
	 */
	@PostMapping("test/async/user")
//	@Transactional // 模拟一个service方法期待是一个事务
	public String testSaveUser(String name) {
		CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
			userInfoService.businessUserMethod(name);
//			transactionHelper.transactional((param)->{ // 通过lambda实现事务管理
//				UserInfo user = userInfoRepository.findById(1L).get();
//				//..... 此处模拟一些业务操作，第一次改变UserInfo里面的值；
//				try {
//					Thread.sleep(200L);// 加上复杂业务耗时200毫秒
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//
//				user.setName(RandomUtils.nextInt(1,100000)+ "_first"+name); //模拟一些业务操作，改变了UserInfo里面的值
//				userInfoRepository.save(user);
//
//				//..... 此处模拟一些业务操作，第二次改变UserInfo里面的值；
//				try {
//					Thread.sleep(300L);// 加上复杂业务耗时300毫秒
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//
//				user.setName(RandomUtils.nextInt(1,100000)+ "_second"+name);//模拟一些业务操作，改变了UserInfo里面的值
//				userInfoRepository.save(user);
//			},name);
		}, executor).exceptionally(e -> {
			log.error(e);//把异常信息打印出来
			return null;
		});

		//... 实际业务中，可能还有会其他异步方法，我们举例一个已经可以说明问题了
		cf.isDone();
		return "Success";
	}
}
