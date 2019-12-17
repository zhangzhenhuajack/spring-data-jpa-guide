package com.example.example2.repository;

import com.example.example2.entity.User;
import com.example.example2.repository.custom.UserRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * 使用的时候直接继承 UserRepositoryCustom接口即可
 */
public interface UserRepository extends Repository<User, Long>,UserRepositoryCustom {
	/**
	 * 根据名称进行查询用户列表
	 * @param name
	 * @return
	 */
	List<User> findByName(String name);
	/**
	 * 根据用户的邮箱和名称查询
	 *
	 * @param email
	 * @param name
	 * @return
	 */
	Page<User> findByEmailAndName(String email, String name, Pageable pageable);
}
