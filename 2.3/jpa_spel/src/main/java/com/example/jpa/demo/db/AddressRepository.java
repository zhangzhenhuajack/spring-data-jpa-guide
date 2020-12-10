package com.example.jpa.demo.db;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long>{
//	List<Address> findByUserIdIn(List<Long> userIds);
@Override
@EntityGraph(value = "getAllUserInfo")
List<Address> findAll();
}
