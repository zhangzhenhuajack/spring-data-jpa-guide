package com.example.example2.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class User {
	private int id;
	private String name;
	private String email;
	private Collection<UserAddress> userAddressesById;

	@Id
	@Column(name = "id", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name", nullable = true, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "email", nullable = true, length = 200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;

		if (id != user.id)
			return false;
		if (name != null ? !name.equals(user.name) : user.name != null)
			return false;
		if (email != null ? !email.equals(user.email) : user.email != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		return result;
	}

	@OneToMany(mappedBy = "userByUserId")
	public Collection<UserAddress> getUserAddressesById() {
		return userAddressesById;
	}

	public void setUserAddressesById(Collection<UserAddress> userAddressesById) {
		this.userAddressesById = userAddressesById;
	}
}
