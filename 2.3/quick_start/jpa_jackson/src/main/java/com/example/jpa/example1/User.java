package com.example.jpa.example1;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "address")
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private String name;
	private String email;
	private String sex;
//	@JsonIgnore
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
//	@JsonBackReference
	private List<UserAddress> address;
//	@OneToOne(mappedBy = "user",fetch = FetchType.LAZY,optional = true)
//	private UserInfo userInfo;
//	@OneToMany(mappedBy = "user")
//	@JsonBackReference
//	private List<UserRoomRelation> userRoomRelations;
//	@ManyToMany(mappedBy = "users")
//	private List<Room> rooms;
}

