package com.example.jpa.demo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

	@Query("select u from User u left join fetch u.roles where u.name=:name")
	List<User> findByName(@Param("name") String name);

	@EntityGraph("user.eagerRoles")
	List<User> findByEmail(String email);
}
