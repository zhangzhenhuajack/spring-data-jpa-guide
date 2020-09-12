package com.example.jpa.example1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class UserInfoRepositoryTest {
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Test
	public void testIdClass() {
		userInfoRepository.save(UserInfo.builder().ages(1).userInfoID(UserInfoID.builder().name("jack").telephone("123456789").build()).build());
		Optional<UserInfo> userInfo = userInfoRepository.findById(UserInfoID.builder().name("jack").telephone("123456789").build());
		System.out.println(userInfo.get());
//		List<UserInfo> userInfos = userInfoRepository.findAll();
//		userInfos.stream().forEach(u->{
//			System.out.println(u.getName());
//		});
	}
}
