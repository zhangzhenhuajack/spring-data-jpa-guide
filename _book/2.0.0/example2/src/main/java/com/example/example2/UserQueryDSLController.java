package com.example.example2;

import com.example.example2.entity.QUser;
import com.example.example2.entity.User;
import com.example.example2.repository.UserQueryDSLRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@RequestMapping("query/dsl/")
public class UserQueryDSLController {
	@Autowired
	private UserQueryDSLRepository userQueryDSLRepository;
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping("users/all")
	public Iterable<User> find() {
		QUser user = QUser.user;
//
//		//查找出Id小于3,并且名称带有`shanghai`的记录.
//		//动态条件
//		QUser qUser = QUser.user;
//		//该Predicate为querydsl下的类,支持嵌套组装复杂查询条件
//		Predicate predicate = qUser.id.longValue().lt(3)
//				.and(qUser.name.like("shanghai"));
//		//分页排序
//		Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC,"id"));
//		PageRequest pageRequest = PageRequest.of(0,10,sort);
//		//查找结果
//		Page<User> tCityPage = userQueryDSLRepository.findAll(predicate,pageRequest);



		Predicate predicate = user.name.startsWith("jack")
				.and(user.email.startsWithIgnoreCase("jack"));
		return userQueryDSLRepository.findAll(predicate);
	}


	@GetMapping("users")
	public Page<User> findByParam(@QuerydslPredicate(root = User.class) Predicate predicate, Pageable pageable) {
		return userQueryDSLRepository.findAll(predicate,pageable);
	}

}
