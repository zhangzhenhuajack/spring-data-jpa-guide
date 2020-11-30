package com.example.jpa.demo.db;

import com.example.jpa.demo.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
//@BatchSize(size = 2)
public class UserInfo extends BaseEntity {
	private String name;
	private String telephone;
	//	@BatchSize(size = 20)
	@OneToMany(mappedBy = "userInfo",cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Address> addressList;
	private Integer ages;
	private String lastName;
	private String emailAddress;
}

