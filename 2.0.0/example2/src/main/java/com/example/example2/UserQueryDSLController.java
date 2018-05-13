package com.example.example2;

import com.example.example2.entity.User;
import com.example.example2.repository.UserQueryDSLRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("query/dsl/")
public class UserQueryDSLController {
	@Autowired
	private UserQueryDSLRepository userQueryDSLRepository;

	@GetMapping("users/all")
	public Iterable<User> find() {
//		QUser user = Quser

//		Predicate predicate = user.name.startsWith("jack")
//				.and(user.email.startsWithIgnoreCase("jack"));
//		return userQueryDSLRepository.findAll(predicate);
		return null;
	}


	@GetMapping("users")
	public Iterable<User> findByParam(@QuerydslPredicate(root = User.class) Predicate predicate) {
//		Predicate predicate = user.name.startsWith("jack")
//				.and(user.email.startsWithIgnoreCase("jack"));
		return userQueryDSLRepository.findAll(predicate);
	}

}
