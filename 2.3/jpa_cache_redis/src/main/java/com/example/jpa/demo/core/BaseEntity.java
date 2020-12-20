package com.example.jpa.demo.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@Version
	private Integer version;
	@CreatedBy
	private Integer createUserId;
	@CreatedDate
	private Instant createTime;
	@LastModifiedBy
	private Integer lastModifiedUserId;
	@LastModifiedDate
	private Instant lastModifiedTime;
}
