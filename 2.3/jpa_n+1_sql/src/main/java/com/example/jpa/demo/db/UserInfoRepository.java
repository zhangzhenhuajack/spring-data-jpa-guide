package com.example.jpa.demo.db;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{
	@Override
	//我们指定EntityGraph引用的是在UserInfo实例里面配置的name=addressGraph的NamedEntityGraph；
	// 这里采用的是LOAD的类型，也就是说被addressGraph配置的实体图属性address采用的fetch会变成 FetchType.EAGER模式，而没有被addressGraph实体图配置关联关系属性room还是采用默认模式EAGER模式
	@EntityGraph(value = "addressGraph",type = EntityGraph.EntityGraphType.LOAD)
	List<UserInfo> findAll();
}
