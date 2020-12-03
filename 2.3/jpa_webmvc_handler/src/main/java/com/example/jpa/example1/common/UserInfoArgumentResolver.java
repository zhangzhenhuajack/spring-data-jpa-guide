package com.example.jpa.example1.common;

import com.example.jpa.example1.UserInfo;
import com.example.jpa.example1.UserInfoRepository;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
// 1. 实现HandlerMethodArgumentResolver接口
//@Component
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {
	private final RedisTemplate redisTemplate;//伪代码，假设我们token是放在redis里面的
	private final UserInfoRepository userInfoRepository;
	public UserInfoArgumentResolver(RedisTemplate redisTemplate, UserInfoRepository userInfoRepository) {
		this.redisTemplate = redisTemplate;//伪代码，假设我们token是放在redis里面的
		this.userInfoRepository = userInfoRepository;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return UserInfo.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest nativeRequest = (HttpServletRequest) webRequest.getNativeRequest();
		String token = nativeRequest.getHeader("token");
		Long userId = (Long) redisTemplate.opsForValue().get(token);//伪代码，假设我们token是放在redis里面的
		UserInfo useInfo = userInfoRepository.getOne(userId);
		return useInfo;
	}
}
