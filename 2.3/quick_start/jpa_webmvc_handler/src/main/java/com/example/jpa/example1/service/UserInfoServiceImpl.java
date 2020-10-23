package com.example.jpa.example1.service;

import com.example.jpa.example1.UserInfo;
import com.example.jpa.example1.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoRepository userInfoRepository;

	/**
	 * 根据UserId产生的一些业务计算逻辑
	 *
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional
	@Retryable(value = ObjectOptimisticLockingFailureException.class,backoff = @Backoff(multiplier = 1.5,random = true))
	public UserInfo calculate(Long userId) {
//		//不带悲观锁的方法
//		UserInfo userInfo = userInfoRepository.getOne(userId);
		UserInfo userInfo = userInfoRepository.findById(userId).get();
		try {
			//模拟复杂的业务计算逻辑耗时操作；
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		userInfo.setAges(userInfo.getAges()+1);
		userInfo.setTelephone(Instant.now().toString());
		return userInfoRepository.saveAndFlush(userInfo);
	}
}
