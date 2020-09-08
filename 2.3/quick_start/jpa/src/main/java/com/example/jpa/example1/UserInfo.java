package com.example.jpa.example1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
	private Integer ages;
	@EmbeddedId
	private UserInfoID userInfoID;
	@Column(unique = true)
	private String uniqueNumber;
//	@Id
//	private String name;
//	@Id
//	private String telephone;
}
