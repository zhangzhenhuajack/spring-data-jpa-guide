package com.example.jpa.example1.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//自定义自己的return的处理类，我们直接继承RequestResponseBodyMethodProcessor，这样父类里面的方法我们直接使用就可以了
@Component
public class MyWarpWithDataHandlerMethodReturnValueHandler extends RequestResponseBodyMethodProcessor implements HandlerMethodReturnValueHandler {
	//参考父类RequestResponseBodyMethodProcessor的做法
	@Autowired
	public MyWarpWithDataHandlerMethodReturnValueHandler(List<HttpMessageConverter<?>> converters) {
		super(converters);
	}

	/**
	 * Basic constructor with converters and {@code ContentNegotiationManager}.
	 * Suitable for resolving {@code @RequestBody} and handling
	 * {@code @ResponseBody} without {@code Request~} or
	 * {@code ResponseBodyAdvice}.
	 *
	 * @param converters
	 * @param manager
	 */
	public MyWarpWithDataHandlerMethodReturnValueHandler(List<HttpMessageConverter<?>> converters, ContentNegotiationManager manager) {
		super(converters, manager);
	}

	/**
	 * Complete constructor for resolving {@code @RequestBody} method arguments.
	 * For handling {@code @ResponseBody} consider also providing a
	 * {@code ContentNegotiationManager}.
	 *
	 * @param converters
	 * @param requestResponseBodyAdvice
	 * @since 4.2
	 */
	public MyWarpWithDataHandlerMethodReturnValueHandler(List<HttpMessageConverter<?>> converters, List<Object> requestResponseBodyAdvice) {
		super(converters, requestResponseBodyAdvice);
	}

	/**
	 * Complete constructor for resolving {@code @RequestBody} and handling
	 * {@code @ResponseBody}.
	 *
	 * @param converters
	 * @param manager
	 * @param requestResponseBodyAdvice
	 */
	public MyWarpWithDataHandlerMethodReturnValueHandler(List<HttpMessageConverter<?>> converters, ContentNegotiationManager manager, List<Object> requestResponseBodyAdvice) {
		super(converters, manager, requestResponseBodyAdvice);
	}

	//我们只处理需要包装的注解的方法
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.hasMethodAnnotation(WarpWithData.class);
	}
	//我们将我们的返回结果包装一层Data
	@Override
	public void handleReturnValue(Object returnValue, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws IOException, HttpMediaTypeNotAcceptableException {
		Map<String,Object> res = new HashMap<>();
		res.put("data",returnValue);
		super.handleReturnValue(res,methodParameter,modelAndViewContainer,nativeWebRequest);
	}
}
