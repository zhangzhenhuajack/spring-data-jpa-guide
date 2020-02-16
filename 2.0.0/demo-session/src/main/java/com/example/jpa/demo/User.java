package com.example.jpa.demo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedEntityGraph(name = "user.eagerRoles",
		attributeNodes = @NamedAttributeNode("roles")
)
public class User {
	@Id
	private Integer id;
	private String name;
	private String email;
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
//	@BatchSize(size=5)
//	@Fetch(FetchMode.JOIN)
	private List<Role> roles = new ArrayList<>();

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
