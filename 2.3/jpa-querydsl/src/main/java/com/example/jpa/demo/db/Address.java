package com.example.jpa.demo.db;

import com.example.jpa.demo.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "userInfo")
//@JsonIgnoreProperties("userInfo")
@NamedEntityGraph(name = "getAllUserInfo",attributeNodes = @NamedAttributeNode(value = "userInfo"))
public class Address extends BaseEntity {
	@JsonProperty("myCity") //改变JSON响应的属性名字
	private String city;
	@JsonIgnore //JSON解析的时候忽略某个属性
	private String address;
	@Transient
	private String addressAndCity;

	public String getAddressAndCity() {
		return address+"一些简单逻辑"+city;
	}

	////	@BatchSize(size = 30)
//	@Fetch(value = FetchMode.JOIN)
//	@Transient
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private UserInfo userInfo;
//	private Long userId;
}
