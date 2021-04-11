package com.example.jpa.demo.db;

import com.example.jpa.demo.core.BaseEntity;
import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class UserInfo extends BaseEntity {
	private String name;
	private String telephone;
	@QueryType(PropertyType.COMPARABLE)
	private Integer ages;
	@BatchSize(size = 20)
	@OneToMany(mappedBy = "userInfo",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private List<Address> addressList;
	@QueryType(PropertyType.STRING)
	private String lastName;
}

