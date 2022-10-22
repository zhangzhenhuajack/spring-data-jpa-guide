package com.example.id_generator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRespository extends JpaRepository<UserInfo,Integer> {
}
