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
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@Version
	private Integer version;
	@CreatedBy //这个可能会被 AuditingEntityListener覆盖，为了方便我们测试，我们先注释掉
	private Integer createUserId;
	@CreatedDate
	private Instant createTime;
	@LastModifiedBy
	private Integer lastModifiedUserId;
	@LastModifiedDate
	private Instant lastModifiedTime;
}
