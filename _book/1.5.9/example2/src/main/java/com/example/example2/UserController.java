package com.example.example2;

import com.example.example2.repository.UserCrudRepository;
import com.example.example2.repository.UserPagingAndSortingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping(path = "/demo")
public class UserController {
	@Autowired
	private UserCrudRepository userRepository;
	@Autowired
	private UserPagingAndSortingRepository userPagingAndSortingRepository;
	@GetMapping(path = "/add")
	public void addNewUser(@RequestParam String name, @RequestParam String email) {
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
	}
	@GetMapping(path = "/all")
	@ResponseBody
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	@GetMapping(path = "/info")
	@ResponseBody
	public User findOne(@RequestParam Long id){
		return userRepository.findOne(id);
	}
	@GetMapping(path = "/delete")
	public void delete(@RequestParam Long id){
		userRepository.delete(id);
	}

	/**
	 * 验证排序和分页查询方法
	 * @return
	 */
	@GetMapping(path = "/page")
	@ResponseBody
	public Page<User> getAllUserByPage() {
		return userPagingAndSortingRepository.findAll(
				new PageRequest(1, 20,new Sort(new Sort.Order(Sort.Direction.ASC,"name"))));
	}

	/**
	 * 排序查询方法
	 * @return
	 */
	@GetMapping(path = "/sort")
	@ResponseBody
	public Iterable<User> getAllUsersWithSort() {
		return userPagingAndSortingRepository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"name")));
	}

}
