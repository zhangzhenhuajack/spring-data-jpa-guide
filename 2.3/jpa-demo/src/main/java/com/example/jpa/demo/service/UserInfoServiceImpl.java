package com.example.jpa.demo.service;

import com.example.jpa.demo.db.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceProperty;

@Component
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoRepository userInfoRepository;
	@PersistenceContext(
			unitName = "db2",//采用数据源2的
			//可以跨事务的EntityManager
			type = PersistenceContextType.EXTENDED,
			properties = {
					//通过properties改变一下自动flush的机制
					@PersistenceProperty(
							name="org.hibernate.flushMode",
							value= ""
					)
			}
	)
	private EntityManager entityManager;
}
