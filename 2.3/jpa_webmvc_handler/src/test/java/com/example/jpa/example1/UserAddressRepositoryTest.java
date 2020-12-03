package com.example.jpa.example1;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserAddressRepositoryTest {
	@Autowired
	private UserAddressRepository userAddressRepository;
	/**
	 * 负责添加数据，假设数据库里面已经有的数据
	 */
	@BeforeAll
	@Rollback(false)
	@Transactional
	void init() {
		User user = User.builder()
				.name("jack")
				.email("123456@126.com")
				.sex(SexEnum.BOY)
				.age(20)
				.build();
		userAddressRepository.saveAll(Lists.newArrayList(UserAddress.builder().user(user).address("shanghai").build(),
				UserAddress.builder().user(user).address("beijing").build()));
	}
}