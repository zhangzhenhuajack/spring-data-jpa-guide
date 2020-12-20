package com.example.jpa.demo.db;

import com.example.jpa.demo.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
	private String city;

////	@BatchSize(size = 30)
//	@Fetch(value = FetchMode.JOIN)
//	@Transient
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private UserInfo userInfo;
//	private Long userId;
}
