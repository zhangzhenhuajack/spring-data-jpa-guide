package com.example.jpa.example1.service;

import com.example.jpa.example1.UserInfo;

public interface UserInfoService {
	/**
	 * 根据UserId产生的一些业务计算逻辑
	 *
	 * @return
	 */
	UserInfo calculate(Long userId);
}
