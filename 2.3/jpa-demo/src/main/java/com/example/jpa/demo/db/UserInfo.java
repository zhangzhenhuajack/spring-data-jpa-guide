package com.example.jpa.demo.db;

import com.example.jpa.demo.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
//@ToString(exclude = "addressList")
//@JsonIgnoreProperties("addressList")
//@BatchSize(size = 2)
//@NamedEntityGraphs(value = {@NamedEntityGraph(name = "addressGraph",attributeNodes = @NamedAttributeNode(value = "addressList")),@NamedEntityGraph(name = "rooms",attributeNodes = @NamedAttributeNode(value = "rooms"))})
public class UserInfo extends BaseEntity {
	private String name;
	private String telephone;
	private Integer ages;
	//	@BatchSize(size = 20)
//	@Fetch(value = FetchMode.SUBSELECT)
//	@Transient
//	@OneToMany(mappedBy = "userInfo",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
//	private List<Address> addressList;
//	@OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
//	private List<Room> rooms;
	private String lastName;
	private String emailAddress;
	private String url;

//	public UserInfo setAddressList(List<Address> addressList) {
//		this.addressList = addressList;
//		return this;
//	}
}

