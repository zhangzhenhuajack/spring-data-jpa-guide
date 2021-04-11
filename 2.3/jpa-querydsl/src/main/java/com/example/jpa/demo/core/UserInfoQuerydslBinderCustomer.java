package com.example.jpa.demo.core;

import com.example.jpa.demo.db.QUserInfo;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public class UserInfoQuerydslBinderCustomer implements QuerydslBinderCustomizer<QUserInfo> {
	/**
	 * Customize the {@link QuerydslBindings} for the given root.
	 *
	 * @param bindings the {@link QuerydslBindings} to customize, will never be {@literal null}.
	 * @param root     the entity root, will never be {@literal null}.
	 */
	@Override
	public void customize(QuerydslBindings bindings, QUserInfo root) {
		bindings.bind(root.lastName).first((path,value)-> path.startsWith(value));
		bindings.bind(root.name).first((path,value)-> path.endsWith(value));
	}
}
