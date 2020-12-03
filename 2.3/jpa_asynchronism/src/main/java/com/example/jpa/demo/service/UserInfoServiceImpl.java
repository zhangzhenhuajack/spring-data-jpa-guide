package com.example.jpa.demo.service;

import com.example.jpa.demo.db.UserInfo;
import com.example.jpa.demo.db.UserInfoRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoRepository userInfoRepository;

	/**
	 * 模拟复杂业务方法
	 */
	@Override
	//加上重试机制，这样当我们发生乐观锁异常的时候，重新重试下面的逻辑，减少请求的失败次数
//	@Retryable(value = ObjectOptimisticLockingFailureException.class,backoff = @Backoff(multiplier = 1.5,random = true))
	//加上事务，这样可以做到原子性，解决事务加到异常方法之外没有任何作用的问题
//	@Transactional 先关闭掉事务
	public void businessUserMethod(String name) {
		UserInfo user = userInfoRepository.findById(1L).get();
		user.setName(RandomUtils.nextInt(1,100000)+ "_first"+name); //模拟一些业务操作，改变了UserInfo里面的值
		UserInfo u2 = userInfoRepository.save(user);
		user.setName(RandomUtils.nextInt(1,100000)+ "_second"+name); //模拟一些业务操作，改变了UserInfo里面的值
		UserInfo u3 = userInfoRepository.save(u2);// 第二次save采用第一次save的返回结果，这样里面带有了最新的version的值，所以也就会保存成功

//		UserInfo u4 = UserInfo.builder().id(1L).version(14).name("_second").build();
//
//		UserInfo u5 = userInfoRepository.save(u4);
//
//		System.out.println(user.toString());
//		System.out.println(u2.toString());
//		System.out.println(u3.toString());
//		System.out.println(u4.toString());
//		System.out.println(u5.toString());
	}
}
