package com.example.jpa.example1;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,UserInfoID> {
}
