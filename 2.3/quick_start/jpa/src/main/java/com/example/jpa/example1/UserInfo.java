package com.example.jpa.example1;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
public class UserInfo {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private Integer ages;
	private String telephone;
//	private Long userId;
	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.LAZY,orphanRemoval = false)
	@JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@MapsId
	private User user;
//	@EmbeddedId
//	private UserInfoID userInfoID;
//	@Column(unique = true)
//	private String uniqueNumber;
//	@Id
//	private String name;
//	@Id
//	private String telephone;
}
