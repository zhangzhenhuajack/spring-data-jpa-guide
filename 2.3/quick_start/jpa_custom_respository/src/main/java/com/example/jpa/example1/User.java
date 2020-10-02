package com.example.jpa.example1;

import com.example.jpa.example1.customized.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "addresses")
@SQLDelete(sql = "UPDATE user SET deleted = true where deleted =false and id = ?")
public class User extends BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private String name;
	private String email;
	@Enumerated(EnumType.STRING)
	private SexEnum sex;
	private Integer age;
	private Instant createDate;
	private Date updateDate;
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<UserAddress> addresses;
	private Boolean deleted;
}
enum SexEnum {
	BOY,GIRL
}
