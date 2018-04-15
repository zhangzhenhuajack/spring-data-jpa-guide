package com.example.example2.entity;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Table(name = "user_address")
public class UserAddress {
	private int id;
	private Integer userId;
	private String city;
	private User userByUserId;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "city", nullable = true, length = 50)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserAddress that = (UserAddress) o;

		if (id != that.id)
			return false;
		if (userId != null ? !userId.equals(that.userId) : that.userId != null)
			return false;
		if (city != null ? !city.equals(that.city) : that.city != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		return result;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id",insertable=false, updatable = false)
	public User getUserByUserId() {
		return userByUserId;
	}

	public void setUserByUserId(User userByUserId) {
		this.userByUserId = userByUserId;
	}
}
