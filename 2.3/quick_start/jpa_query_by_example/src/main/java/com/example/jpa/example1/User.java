package com.example.jpa.example1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
@ToString(exclude = "address")
public class User implements Serializable {
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
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
	@JsonIgnore
	private List<UserAddress> address;
}
enum SexEnum {
	BOY,GIRL
}
