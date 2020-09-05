package com.example.jpa.example1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class UserRepositoryQueryTest {
	@Autowired
	private UserDtoRepository userDtoRepository;
	@Autowired
	private UserExtendRepository userExtendRepository;

	@Test
	public void testQueryAnnotation() {
		userDtoRepository.save(User.builder().name("jack").email("123456@126.com").sex("man").address("shanghai").build());
		userExtendRepository.save(UserExtend.builder().userId(1L).idCard("shengfengzhenghao").ages(18).studentNumber("xuehao001").build());
		List<Object[]> userArray = userDtoRepository.findByUserId(1L);
		System.out.println(String.valueOf(userArray.get(0)[0])+String.valueOf(userArray.get(0)[1]));
		UserDto userDto = UserDto.builder().name(String.valueOf(userArray.get(0)[0])).build();
		System.out.println(userDto);
//		User user2 = userDtoRepository.findByQuery("jack");
//		System.out.println(user2);
//		User user = userDtoRepository.findByEmail("123456@126.com");
//		System.out.println(user);
	}


	@Test
	public void testQueryAnnotationDto() {
		userDtoRepository.save(User.builder().name("jack").email("123456@126.com").sex("man").address("shanghai").build());
		userExtendRepository.save(UserExtend.builder().userId(1L).idCard("shengfengzhenghao").ages(18).studentNumber("xuehao001").build());
		UserSimpleDto userDto = userDtoRepository.findByUserSimpleDtoId(1L);
		System.out.println(userDto);
		System.out.println(userDto.getName()+":"+userDto.getEmail()+":"+userDto.getIdCard());
//		UserDto userDto = userDtoRepository.findByUserDtoId(1L);
//		System.out.println(userDto);
	}

	@Test
	public void testQueryDinamicDto() {
		userDtoRepository.save(User.builder().name("jack").email("123456@126.com").sex("man").address("shanghai").build());
		UserOnlyName userDto = userDtoRepository.findByUser("jack", null);
		System.out.println(userDto.getName() + ":" + userDto.getEmail());

		UserOnlyName userDto2 = userDtoRepository.findByUser(User.builder().email("123456@126.com").build());
		System.out.println(userDto2.getName() + ":" + userDto2.getEmail());
	}
}
