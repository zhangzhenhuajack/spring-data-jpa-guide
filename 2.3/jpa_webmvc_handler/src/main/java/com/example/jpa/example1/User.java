package com.example.jpa.example1;

import com.example.jpa.example1.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "addresses",callSuper = true)
@EqualsAndHashCode(callSuper=false)
@DynamicInsert
@DynamicUpdate
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
