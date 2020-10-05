package com.example.jpa.example1.base;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@CreatedBy
	private Integer createUserId;
	@CreatedDate
	private Instant createTime;
	@LastModifiedBy
	private Integer lastModifiedUserId;
	@LastModifiedDate
	private Instant lastModifiedTime;
	@Version
	private Integer version;

	@PreUpdate
	public void preUpdate() {
		System.out.println("preUpdate::"+this.toString());
		this.setCreateUserId(200);
	}

	@PostUpdate
	public void postUpdate() {
		System.out.println("postUpdate::"+this.toString());
		this.setCreateUserId(300);
	}

	@PreRemove
	public void preRemove() {
		System.out.println("preRemove::"+this.toString());
	}

	@PostRemove
	public void postRemove() {
		System.out.println("postRemove::"+this.toString());
	}


	@PostLoad
	public void postLoad() {
		System.out.println("postLoad::"+this.toString());
	}
}
