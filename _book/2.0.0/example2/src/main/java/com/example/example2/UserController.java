package com.example.example2;

import com.example.example2.entity.User;
import com.example.example2.repository.UserCrudRepository;
import com.example.example2.repository.UserPagingAndSortingRepository;
import com.example.example2.repository.UserRepository;
import com.example.example2.repository.custom.MySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping(path = "/demo")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCrudRepository userCrudRepository;
	@Autowired
	private UserPagingAndSortingRepository userPagingAndSortingRepository;
	@GetMapping(path = "/add")
	public void addNewUser(@RequestParam String name, @RequestParam String email) {
		User n = new User();
		n.setName(name);
		n.setEmail(email);
        userCrudRepository.save(n);
	}
	@GetMapping(path = "/all")
	@ResponseBody
	public Iterable<User> getAllUsers() {
		return userCrudRepository.findAll();
	}
	@GetMapping(path = "/info")
	@ResponseBody
	public Optional<User> findOne(@RequestParam Long id){
		return userCrudRepository.findById(1L);
	}
	@GetMapping(path = "/delete")
	public void delete(@RequestParam Long id){
        userCrudRepository.deleteById(id);
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

    /**
     * 调用我们自定义的实现方法
     *
     * @return
     */
    @GetMapping(path = "/customer")
    @ResponseBody
    public Iterable<User> findCustomerMethodNamesLike() {
        return userRepository.customerMethodNamesLike("jack");
    }

	public void MySpecificationTest() {
//		Page pageData = userDao.findAll(, pr);
	}
}
