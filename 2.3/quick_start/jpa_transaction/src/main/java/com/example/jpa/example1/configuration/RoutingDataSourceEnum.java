package com.example.jpa.example1.configuration;

/**
 * 定义一个数据源的枚举类
 */
public enum RoutingDataSourceEnum {
	DB1,
	DB2;
	public static RoutingDataSourceEnum findByCode(String dbRouting) {
		for (RoutingDataSourceEnum e : values()) {
			if (e.name().equals(dbRouting)) {
				return e;
			}
		}
		return DB1;//没找到的情况下，默认返回数据源1
	}
}
