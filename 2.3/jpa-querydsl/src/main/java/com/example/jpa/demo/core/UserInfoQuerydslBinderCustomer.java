package com.example.jpa.demo.core;

import com.example.jpa.demo.db.QUserInfo;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public class UserInfoQuerydslBinderCustomer implements QuerydslBinderCustomizer<QUserInfo> {
	//直接实现这个接口，自定义
	@Override
	public void customize(QuerydslBindings bindings, QUserInfo root) {
		bindings.bind(root.lastName).first((path,value)-> path.startsWith(value));
		bindings.bind(root.name).first((path,value)-> path.endsWith(value));
	}
}
