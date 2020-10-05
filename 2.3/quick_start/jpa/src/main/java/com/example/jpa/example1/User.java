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
public class User extends BaseEntity {// implements Auditable<Integer,Long, Instant> {
	private String name;
	private String email;
	@Enumerated(EnumType.STRING)
	private SexEnum sex;
	private Integer age;
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<UserAddress> addresses;
	private Boolean deleted;

	@PrePersist
	private void prePersist() {
		System.out.println("prePersist::"+this.getVersion());
		System.out.println("prePersist::"+this.getCreateTime());
		this.setVersion(1);
	}

	@PostPersist
	public void postPersist() {
		System.out.println("postPersist::"+this.getVersion());
		System.out.println("postPersist::"+this.getCreateTime());
		this.setVersion(2);
//		throw new RuntimeException("dddd");
	}

}
enum SexEnum {
	BOY,GIRL
}
