package com.example.jpa.demo.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
@RepositoryRestResource(
		exported = true,
		path = "users",
		collectionResourceRel = "userInfo",
		collectionResourceDescription = @Description("用户资源"),
		itemResourceRel = "userDetail",
		itemResourceDescription = @Description("用户详情")
)
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	//没用用@Query，直接使用method name defining query
	List<UserInfo> findByNameAndCreateTimeBetween(String name, Instant begin, Instant endTime);

	//演示SpEL根据数组下标取参数，和根据普通的Parma的名字:name取参数
	@Query("select u from UserInfo u where u.lastName like %:#{[0]} and u.name like %:name%")
	List<UserInfo> findContainingEscaped(@Param("name") String name);

	//SpEL取Parma的名字customer里面的属性
	@Query("select u from UserInfo u where u.name = :#{#customer.name}")
	List<UserInfo> findUsersByCustomersFirstname(@Param("customer") UserInfo customer);

	//利用SpEL根据一个写死的'jack'字符串作为参数
	@Query("select u from UserInfo u where u.name = ?#{'jack'}")
	List<UserInfo> findOliverBySpELExpressionWithoutArgumentsWithQuestionmark();

	//测试IN查询条件的情况
	List<UserInfo> findByNameAndUrlIn(String name, Collection<String> urls);

	@Query(value = "select * from user_info where name=:name",nativeQuery = true)
	List<UserInfo> findByName(@Param(value = "name") String name);

}
