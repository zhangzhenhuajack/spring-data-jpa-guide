package com.example.jpa.demo.db;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

    @Query("From UserInfo where id=2")
    List<UserInfo> queryByFlushTest() ;
}
