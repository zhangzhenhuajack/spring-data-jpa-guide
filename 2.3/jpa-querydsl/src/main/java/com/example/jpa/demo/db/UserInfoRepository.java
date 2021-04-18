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
	 * Customize the {@link QuerydslBindings} for the given root.
	 *
	 * @param bindings the {@link QuerydslBindings} to customize, will never be {@literal null}.
	 * @param root     the entity root, will never be {@literal null}.
	 */
	@Override
	default void customize(QuerydslBindings bindings, QUserInfo root) {
		bindings.bind(root.lastName).first((path,value)-> path.contains(value));
		bindings.bind(root.name).first((path,value)-> path.startsWith(value));
		bindings.bind(root.createTime).all(((path, values) -> { //时间范围查询
			Iterator<Instant> iterator = (Iterator<Instant>) values.iterator();
			return java.util.Optional.of(path.between(iterator.next(), iterator.next())); //因此页面传递参数时，第一个element为小值，第二个为大值
		}));
	};
}
