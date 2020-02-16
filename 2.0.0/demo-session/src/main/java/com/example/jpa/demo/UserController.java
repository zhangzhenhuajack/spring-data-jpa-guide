package com.example.jpa.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private UserRepository userRepository;

	/**
	 * 测试事务和session的关系；
	 * @param id
	 * @return
	 */
	@RequestMapping("test")
	public User testOk(Integer id) {
		User user = new User();
		user.setId(id);
		user.setName("name" + id);
		user.setEmail("email" + id);
		userServiceImpl.updateUser(user);
		return userServiceImpl.findById(id);
	}
	@RequestMapping("save")
	public void save(Integer id){
		for (int i=0;i<10;i++) {
			User user = new User();
			user.setName("jack");
			user.setEmail("email");
			user.setId(id+i);
			user.getRoles().add(new Role(3, "jack_role3"));
			user.getRoles().add(new Role(2, "jack_role2"));

			userRepository.save(user);
		}
	}
	@RequestMapping("test3")
	public String testFetch() {
//		userServiceImpl.testFetch();
//		userRepository.findByName("jack");
		userRepository.findByEmail("email");
		return "success";
	}
	/**
	 * 测试 flush sql执行顺序
	 * @param id
	 * @return
	 */
	@RequestMapping("test2")
	public String testFlushOrder(Integer id) {
		userServiceImpl.testFlushOrder(id);
		return "success";
	}
}










