package com.example.jpa.quick;

import com.example.jpa.quick.dao.UserInfoRepository;
import com.example.jpa.quick.entity.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserInfoRepositoryTest {
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Test
	public void testVersion() {
		userInfoRepository.save(UserInfo.builder().age(20).email("1233456@mail.com").build());
		UserInfo userInfo = userInfoRepository.getOne(1L);
		System.out.println(userInfo);
		Assertions.assertEquals(0,userInfo.getVersion());
		Assertions.assertEquals(20,userInfo.getAge());
		userInfo.setAge(30);
		userInfoRepository.saveAndFlush(userInfo);

		UserInfo u2 = userInfoRepository.getOne(1L);
		Assertions.assertEquals(1,u2.getVersion());
		Assertions.assertEquals(30,u2.getAge());
	}
}
