package com.example.jpa.example1.configuration;

/**
 * 利用ThreadLocal用来存储，当前的线程使用的哪个数据
 */
public class DataSourceRoutingHolder {
	private static ThreadLocal<RoutingDataSourceEnum> threadLocal = new ThreadLocal<>();
	public static void setBranchContext(RoutingDataSourceEnum dataSourceEnum) {
		threadLocal.set(dataSourceEnum);
	}
	public static RoutingDataSourceEnum getBranchContext() {
		return threadLocal.get();
	}
	public static void clearBranchContext() {
		threadLocal.remove();
	}
}
