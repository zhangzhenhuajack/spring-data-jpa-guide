package com.example.jpa.example1.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 继承父类，可以省掉很多计算page和index的逻辑
 */
public class MyPageRequest extends PageRequest {
	protected MyPageRequest(int page, int size, Sort sort) {
		super(page, size, sort);
	}
}
