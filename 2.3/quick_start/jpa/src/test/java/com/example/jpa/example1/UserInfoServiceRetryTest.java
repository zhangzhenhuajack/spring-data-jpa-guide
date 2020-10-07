package com.example.jpa.example1;

import com.example.jpa.example1.configuration.RetryConfiguration;
import com.example.jpa.example1.service.UserInfoService;
import com.example.jpa.example1.service.UserInfoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackageClasses=UserInfoServiceImpl.class)
@Import(RetryConfiguration.class)
public class UserInfoServiceRetryTest {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Test
	@Rollback(false)
	@Transactional(propagation = Propagation.NEVER)
	public void testRetryable() {
		//加一条数据
		userInfoRepository.saveAndFlush(UserInfo.builder().ages(20).telephone("1233456").build());

		//模拟多线程执行两次
		new Thread(() -> userInfoService.calculate(1L)).start();
		try {
			Thread.sleep(10L);//
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//模拟多线程执行两次，由于加了@EnableRetry，所以这次也会成功
		UserInfo userInfo = userInfoService.calculate(1L);
		//经过了两次计算年龄变成了22
		Assertions.assertEquals(22,userInfo.getAges());
		Assertions.assertEquals(2,userInfo.getVersion());
	}
}
