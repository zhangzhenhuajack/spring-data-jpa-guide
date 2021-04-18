package com.example.jpa.demo.db;

import com.example.jpa.demo.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

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
	private String address;

	@BatchSize(size = 30)
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private UserInfo userInfo;
}
