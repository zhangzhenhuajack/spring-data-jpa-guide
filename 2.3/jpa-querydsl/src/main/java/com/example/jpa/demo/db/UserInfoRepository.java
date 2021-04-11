package com.example.jpa.demo.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author jack
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, QuerydslPredicateExecutor<UserInfo> {

}
