package com.example.jpa.example1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private String name;
	private String email;
	private String sex;
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	private List<UserAddress> address;
	@OneToOne(mappedBy = "user",fetch = FetchType.LAZY,optional = true)
	private UserInfo userInfo;
	@OneToMany(mappedBy = "user")
	private List<UserRoomRelation> userRoomRelations;
//	@ManyToMany(mappedBy = "users")
//	private List<Room> rooms;
}

