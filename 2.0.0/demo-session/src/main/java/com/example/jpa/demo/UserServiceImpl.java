package com.example.jpa.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class UserServiceImpl {
	@Autowired
	private UserRepository userRepository;

	@Transactional(propagation = REQUIRES_NEW)
	public User updateUser(User user) {
		return userRepository.save(user);
	}
	@Transactional(propagation = REQUIRES_NEW)
	public User findById(Integer id) {
		return userRepository.findById(id).get();
	}
	@Transactional
	public void testFlushOrder(int id) {
		userRepository.deleteById(id);
		User user = new User();
		user.setId(new Random().nextInt()+id);
		user.setName(user.getName()+ new Random().nextInt());
		userRepository.save(user);
	}
	@Transactional
	public void testFetch(){
		List<User> userList = userRepository.findAll();
//		userList.forEach(u->{
//			System.out.println("aaa"+u.getId());
//			u.getRoles().forEach(r->{
//				System.out.println(r.getName());
//			});
//		});
	}
}
