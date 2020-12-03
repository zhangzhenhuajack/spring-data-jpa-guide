package com.example.jpa.example1;

import lombok.*;

import javax.persistence.*;

/**
 * 用户地址表
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
public class UserAddress {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private String address;
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;
}
