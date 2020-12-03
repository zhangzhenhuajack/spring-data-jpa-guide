package com.example.jpa.demo.db;

import com.example.jpa.demo.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
@ToString(exclude = "addressList")
//@JsonIgnoreProperties("addressList")
public class UserInfo extends BaseEntity {
	private String name;
	private Integer ages;
	private String lastName;
	private String emailAddress;
	private String telephone;
	@OneToMany(mappedBy = "userInfo",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)

	private List<Address> addressList;
}

