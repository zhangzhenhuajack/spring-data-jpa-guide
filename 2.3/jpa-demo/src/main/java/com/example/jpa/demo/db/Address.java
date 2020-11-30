package com.example.jpa.demo.db;

import com.example.jpa.demo.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "userInfo")
public class Address extends BaseEntity {
	private String city;
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JsonBackReference
//	@BatchSize(size = 30)
	@Fetch(value = FetchMode.JOIN)
	private UserInfo userInfo;
}
