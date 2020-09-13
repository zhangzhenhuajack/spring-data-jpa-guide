package com.example.jpa.example1;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
public class UserInfo implements Serializable {
//	@Id
//	@GeneratedValue(strategy= GenerationType.AUTO)
//	private Long id;
	@Id
    Long id;
	@Column(name="user_id",insertable = false,updatable = false,nullable = true)
	private Long userId;

	private Integer ages;
	private String telephone;
	@MapsId
	@OneToOne(cascade = {CascadeType.PERSIST},orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private User user;
//	private Long userId;
//	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})//,fetch = FetchType.LAZY,orphanRemoval = false)
//	@JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//	@MapsId
//	@EmbeddedId
//	private UserInfoID userInfoID;
//	@Column(unique = true)
//	private String uniqueNumber;
//	@Id
//	private String name;
//	@Id
//	private String telephone;
}
