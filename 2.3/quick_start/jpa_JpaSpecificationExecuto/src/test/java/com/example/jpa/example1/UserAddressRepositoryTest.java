package com.example.jpa.example1;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserAddressRepositoryTest {
	@Autowired
	private UserAddressRepository userAddressRepository;
	private Date now = new Date();
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
				.createDate(Instant.now())
				.updateDate(now)
				.build();
		userAddressRepository.saveAll(Lists.newArrayList(UserAddress.builder().user(user).address("shanghai").build(),
				UserAddress.builder().user(user).address("beijing").build()));
	}
	@Test
	@Rollback(false)
	public void testQBEFromUserAddress() throws JsonProcessingException {
		User request = User.builder()
				.name("jack").age(20).email("12345633")
				.build();
		UserAddress address = UserAddress.builder().address("shang").user(request).build();

		ObjectMapper objectMapper = new ObjectMapper();
//		System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(address));
		//创建匹配器，即如何使用查询条件
		ExampleMatcher exampleMatcher = ExampleMatcher
				//采用默认and的查询方式
				.matchingAll()
				//忽略大小写
				.withIgnoreCase()
				//忽略所有null值的字段
				.withIgnoreNullValues()
				.withIgnorePaths("id","createDate")
				//默认采用精准匹配规则
				.withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
				//级联查询,字段user.email采用字符前缀匹配规则
				.withMatcher("user.email", ExampleMatcher.GenericPropertyMatchers.startsWith())
				//特殊指定address字段采用后缀匹配
				.withMatcher("address", ExampleMatcher.GenericPropertyMatchers.endsWith());

		Page<UserAddress> u = userAddressRepository.findAll(Example.of(address,exampleMatcher), PageRequest.of(0,2));

		System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(u));
	}
}