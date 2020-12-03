package com.example.jpa.example1.web;

import com.example.jpa.example1.configuration.DataSourceRoutingHolder;
import com.example.jpa.example1.configuration.RoutingDataSourceEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 动态路由的实现逻辑，我们通过请求头里面的db-routing来指定此请求采用什么数据源
 */
@Component
public class DataSourceInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 请求处理之前更改线程里面的数据源
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
		String dbRouting = request.getHeader("db-routing");
		DataSourceRoutingHolder.setBranchContext(RoutingDataSourceEnum.findByCode(dbRouting));
		return super.preHandle(request, response, handler);
	}

	/**
	 * 请求结束之后清理线程里面的数据源
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
		DataSourceRoutingHolder.clearBranchContext();
	}
}
