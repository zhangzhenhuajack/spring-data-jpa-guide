package com.example.jpa.example1.configuration;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Optional;

/**
 * 继承AbstractRoutingDataSource，根据我们当前线程里面存储的值
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
	//采用的数据源的key是什么，如果找不到，默认给这个DB1
	@Override
	protected Object determineCurrentLookupKey() {
		return Optional.ofNullable(DataSourceRoutingHolder.getBranchContext()).orElse(RoutingDataSourceEnum.DB1);
	}
}
