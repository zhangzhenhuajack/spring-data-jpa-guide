package com.example.jpa.example1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDtoRepository extends JpaRepository<User, Long> {
	/**
	 * 利用 JQPl动态查询用户信息
	 * @param name
	 * @param email
	 * @return UserSimpleDto接口
	 */
	@Query("select u.name as name,u.email as email from User u where (:name is null or u.name =:name) and (:email is null or u.email =:email)")
	UserOnlyName findByUser(@Param("name") String name,@Param("email") String email);

	/**
	 * 利用原始sql动态查询用户信息
	 * @param user
	 * @return
	 */
	@Query(value = "select u.name as name,u.email as email from user u where (:#{#user.name} is null or u.name =:#{#user.name}) and (:#{#user.email} is null or u.email =:#{#user.email})",nativeQuery = true)
	UserOnlyName findByUser(@Param("user") User user);


	@Query("select CONCAT(u.name,'JK123') as name,UPPER(u.email) as email ,e.idCard as idCard from User u,UserExtend e where u.id= e.userId and u.id=:id")
	UserSimpleDto findByUserSimpleDtoId(@Param("id") Long id);
	@Query("select new com.example.jpa.example1.UserDto(CONCAT(u.name,'JK123'),u.email,e.idCard) from User u,UserExtend e where u.id= e.userId and u.id=:id")
	UserDto findByUserDtoId(@Param("id") Long id);
	/**
	 * 查询用户表里面的name、email和UserExtend表里面的idCard
	 *
	 * @param id
	 * @return
	 */
	@Query("select u.name,u.email,e.idCard from User u,UserExtend e where u.id= e.userId and u.id=:id")
	List<Object[]> findByUserId(@Param("id") Long id);

	@Query("From User where name=:name")
	User findByQuery(@Param("name") String nameParam);
}