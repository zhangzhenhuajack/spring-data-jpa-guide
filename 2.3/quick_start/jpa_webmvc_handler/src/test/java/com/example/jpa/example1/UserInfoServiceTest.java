package com.example.jpa.example1;

import com.example.jpa.example1.service.UserInfoService;
import com.example.jpa.example1.service.UserInfoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackageClasses=UserInfoServiceImpl.class)
public class UserInfoServiceTest {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Test
	@Rollback(false)
	public void testVersion() {
		UserInfo u1 = UserInfo.builder().ages(20).id(1L).telephone("1233456").build();
		u1.setVersion(0);
		//加一条数据
		UserInfo userInfo = userInfoRepository.save(u1);
		//验证一下数据库里面的值
		Assertions.assertEquals(0,userInfo.getVersion());
		Assertions.assertEquals(20,userInfo.getAges());
		userInfoService.calculate(1L);

		//验证一下更新成功的值
		UserInfo u2 =  userInfoRepository.getOne(1L);
		Assertions.assertEquals(1,u2.getVersion());
		Assertions.assertEquals(21,u2.getAges());

	}

	/**
	 * 测试这个的时候需要把上面的
	 */
	@Test
	@Rollback(false)
	@Transactional(propagation = Propagation.NEVER)
	public void testVersionException() {
		//加一条数据
		userInfoRepository.saveAndFlush(UserInfo.builder().ages(20).telephone("1233456").build());

		//模拟多线程执行两次
		new Thread(() -> userInfoService.calculate(1L)).start();
		try {
			Thread.sleep(10L);//
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//如果两个线程同时执行会发生乐观锁异常；
		Exception exception = Assertions.assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
			userInfoService.calculate(1L);
			//模拟多线程执行两次
		});
		System.out.println(exception);
	}
}
