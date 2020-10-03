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
@ToString(exclude = "addresses")
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
//	@CreatedBy
//	private Integer createUserId;
//	@CreatedDate
//	private Instant createTime;
//	@LastModifiedBy
//	private Integer lastModifiedUserId;
//	@LastModifiedDate
//	private Instant lastModifiedTime;
//	@Override
//	public Optional<Integer> getCreatedBy() {
//		return Optional.ofNullable(this.createUserId);
//	}
//	@Override
//	public void setCreatedBy(Integer createdBy) {
//		this.createUserId = createdBy;
//	}
//	@Override
//	public Optional<Instant> getCreatedDate() {
//		return Optional.ofNullable(this.createTime);
//	}
//	@Override
//	public void setCreatedDate(Instant creationDate) {
//		this.createTime = creationDate;
//	}
//	@Override
//	public Optional<Integer> getLastModifiedBy() {
//		return Optional.ofNullable(this.lastModifiedUserId);
//	}
//	@Override
//	public void setLastModifiedBy(Integer lastModifiedBy) {
//		this.lastModifiedUserId = lastModifiedBy;
//	}
//	@Override
//	public void setLastModifiedDate(Instant lastModifiedDate) {
//		this.lastModifiedTime = lastModifiedDate;
//	}
//	@Override
//	public Optional<Instant> getLastModifiedDate() {
//		return Optional.ofNullable(this.lastModifiedTime);
//	}
//	@Override
//	public boolean isNew() {
//		return id==null;
//	}
}
enum SexEnum {
	BOY,GIRL
}
