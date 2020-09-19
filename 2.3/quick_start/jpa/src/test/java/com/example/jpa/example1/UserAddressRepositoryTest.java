package com.example.jpa.example1;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
	@Autowired
	private UserRepository userRepository;
	/**
	 * 负责添加数据
	 */
	@BeforeAll
	@Rollback(false)
	@Transactional
	void init() {
		User user = User.builder().name("jackxx").email("123456@126.com").build();
		UserAddress userAddress = UserAddress.builder().address("shanghai1").user(user).build();
		UserAddress userAddress2 = UserAddress.builder().address("shanghai2").user(user).build();
		userAddressRepository.saveAll(Lists.newArrayList(userAddress,userAddress2));
	}
	/**
	 * 测试用User关联关系操作
	 * @throws JsonProcessingException
	 */
	@Test
	@Rollback(false)
	public void testUserRelationships() throws JsonProcessingException {
		User user = userRepository.getOne(2L);
		System.out.println(user.getName());
		System.out.println(user.getAddress());
	}
	@Test
	@Rollback(false)
	public void testJackson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.registerModule(new Hibernate5Module().configure(Hibernate5Module.Feature.REPLACE_PEhiRSISTENT_COLLECTIONS,true));
		User user = userRepository.getOne(2L);
		System.out.println(user);
		System.out.println(user.getAddress().get(0).getAddress());
		String json = objectMapper.writeValueAsString(user);
		System.out.println(json);
	}
}