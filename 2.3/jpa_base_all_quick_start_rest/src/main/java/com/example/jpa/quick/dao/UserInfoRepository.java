package com.example.jpa.quick.dao;

import com.example.jpa.quick.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author jack
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Long>, JpaSpecificationExecutor<UserInfo>{
}
