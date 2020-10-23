package com.example.jpa.example1;

import org.springframework.data.web.JsonPath;
import org.springframework.data.web.ProjectedPayload;

@ProjectedPayload
public interface UserInfoInterface {
	@JsonPath("$.ages") // 第一级json里面找ages字段
//	@JsonPath("$..ages") $..代表任意层级找ages字段
	Integer getAges();
	@JsonPath("$.telephone") //第一级找json里面的telephone字段
//	@JsonPath({ "$.telephone", "$.user.telephone" }) //第一级或者user下面的telephone都可以
	String getTelephone();
}
