package com.example.jpa.example1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class UserController {
//	@Autowired
//	private UserRepository userRepository;
//
//	/**
//	 * 保存用户
//	 * @param user
//	 * @return
//	 */
//	@PostMapping(path = "user",consumes = {MediaType.APPLICATION_JSON_VALUE})
//	public User addNewUser(@RequestBody User user) {
//		return userRepository.save(user);
//	}
//
//	/**
//	 * 根据分页信息查询用户
//	 * @param request
//	 * @return
//	 */
//	@GetMapping(path = "users")
//	@ResponseBody
//	public Page<User> getAllUsers(Pageable request) {
//		return userRepository.findAll(request);
//	}
//	/**
//	 * 验证排序和分页查询方法，Pageable的默认实现类：PageRequest
//	 * @return
//	 */
//	@GetMapping(path = "/page")
//	@ResponseBody
//	public Page<User> getAllUserByPage() {
//		return userRepository.findAll(
//				PageRequest.of(1, 20,Sort.by(new Sort.Order(Sort.Direction.ASC,"name"))));
//	}
//	/**
//	 * 排序查询方法，使用Sort对象
//	 * @return
//	 */
//	@GetMapping(path = "/sort")
//	@ResponseBody
//	public Iterable<User> getAllUsersWithSort() {
//		return userRepository.findAll(Sort.by(new Sort.Order(Sort.Direction.ASC,"name")));
//	}
}
