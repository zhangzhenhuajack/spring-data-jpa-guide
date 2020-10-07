package com.example.jpa.example1;

import com.example.jpa.example1.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "addresses",callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class User extends BaseEntity {
	private String name;
	private String email;
	@Enumerated(EnumType.STRING)
	private SexEnum sex;
	private Integer age;
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<UserAddress> addresses;
	private Boolean deleted;
}
enum SexEnum {
	BOY,GIRL
}
