package com.example.jpa.example1;

import com.example.jpa.example1.base.BaseEntity;
import com.example.jpa.example1.base.EntityLoggingListener;
import com.example.jpa.example1.base.UserListener;
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
@EntityListeners(UserListener.class)
public class User extends BaseEntity {// implements Auditable<Integer,Long, Instant> {
	@Transient
	public void calculationAge() {
		//通过一些逻辑计算年龄；
		this.age=10;
	}
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
