package com.example.jpa.example1.base;

import lombok.Data;

import javax.persistence.*;
@Data
@MappedSuperclass
public class BaseEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@Version
	private Integer version;
}
