package com.example.example2.repository;

import com.example.example2.entity.User;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author jack
 */
public interface UserQueryDSLRepository extends CrudRepository<User, Long>, QuerydslPredicateExecutor<User> {

}
