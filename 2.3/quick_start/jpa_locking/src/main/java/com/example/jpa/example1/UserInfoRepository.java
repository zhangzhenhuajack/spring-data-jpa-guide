package com.example.jpa.example1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<UserInfo> findById(Long userId);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    UserInfo saveAndFlush(UserInfo entity);
}
