package com.example.jpa.demo.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.time.Instant;
import java.util.Iterator;

/**
 * @author jack
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, QuerydslPredicateExecutor<UserInfo>, QuerydslBinderCustomizer<QUserInfo> {
	/**
	 * 自定义QuerydslBinds，覆盖默认实现
	 *
	 * @param bindings 自定义binds
	 * @param root     实体的root，这里指QUserInfo
	 */
	@Override
	default void customize(QuerydslBindings bindings, QUserInfo root) {
		bindings.bind(root.lastName).first((path,value)-> path.contains(value));
		bindings.bind(root.name).first((path,value)-> path.startsWith(value));
		//大于某一个年龄
		bindings.bind(root.ages).first((path,value)-> path.gt(value));
		bindings.bind(root.createTime).all(((path, values) -> {
			// createTime范围查询
			Iterator<Instant> iterator = (Iterator<Instant>) values.iterator();
			//因此页面传递参数时，第一个element为小值，第二个为大值
			return java.util.Optional.of(path.between(iterator.next(), iterator.next()));
		}));
		//查询管理关系
		bindings.bind(root.addressList.any().city).all((path, value) -> java.util.Optional.ofNullable(path.in(value)));
	};
}
