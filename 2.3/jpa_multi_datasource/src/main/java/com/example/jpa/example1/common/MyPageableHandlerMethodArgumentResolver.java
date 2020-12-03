package com.example.jpa.example1.common;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 通过@Component把此类加载到Spring的容器里面去
 */
@Component
public class MyPageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	//我们假设sort的参数没有发生变化，采用PageableHandlerMethodArgumentResolver里面的写法
	private static final SortHandlerMethodArgumentResolver DEFAULT_SORT_RESOLVER = new SortHandlerMethodArgumentResolver();
	//给定两个默认值
	private static final Integer DEFAULT_PAGE = 0;
	private static final Integer DEFAULT_SIZE = 10;
	//兼容新版我们引入JPA的分页参数
	private static final String JPA_PAGE_PARAMETER = "page";
	private static final String JPA_SIZE_PARAMETER = "size";
	//兼容原来老的分页参数
	private static final String DEFAULT_PAGE_PARAMETER = "page[number]";
	private static final String DEFAULT_SIZE_PARAMETER = "page[size]";
	private SortArgumentResolver sortResolver;
	//模仿PageableHandlerMethodArgumentResolver里面的构造方法
	public MyPageableHandlerMethodArgumentResolver(@Nullable SortArgumentResolver sortResolver) {
		this.sortResolver = sortResolver == null ? DEFAULT_SORT_RESOLVER : sortResolver;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
//		我们假设用我们自己的类MyPageRequest接收参数
//		return MyPageRequest.class.equals(parameter.getParameterType());
		//同时我们也可以支持通过spring data jpa里面的Pageable参数进行接收，两种效果是一样的
		return Pageable.class.equals(parameter.getParameterType());
	}

	/**
	 * 参数封装逻辑page和sort，JPA参数的优先级高于 page[number]和page[size] 参数
	 */
	@Override
//	public MyPageRequest resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
	public Pageable resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		String jpaPageString = webRequest.getParameter(JPA_PAGE_PARAMETER);
		String jpaSizeString = webRequest.getParameter(JPA_SIZE_PARAMETER);
		//我们分别取参数里面page、sort和 page[number]、page[size]的值
		String pageString = webRequest.getParameter(DEFAULT_PAGE_PARAMETER);
		String sizeString = webRequest.getParameter(DEFAULT_SIZE_PARAMETER);
		//当两个都有值的时候的优先级，及其默认值的逻辑
		Integer page = jpaPageString != null ? Integer.valueOf(jpaPageString) : pageString != null ? Integer.valueOf(pageString) : DEFAULT_PAGE;
		//在这里同时可以计算 page+1的逻辑;如：page=page+1;
		Integer size = jpaSizeString != null ? Integer.valueOf(jpaSizeString) : sizeString != null ? Integer.valueOf(sizeString) : DEFAULT_SIZE;

		//我们假设，sort排序的取值方法我们先不发生改变
		Sort sort = sortResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
//		如果我们使用Pageable参数接收接收值，我们也可以不用自定义MyPageRequest对象，直接返回 PageRequest;
		return PageRequest.of(page,size,sort);
		//将page和size计算出来的记过封装到我们自定义的MyPageRequest类里面去
//		MyPageRequest myPageRequest = new MyPageRequest(page, size,sort);
		//返回controller里面的参数需要的对象；
//		return myPageRequest;
	}
}
