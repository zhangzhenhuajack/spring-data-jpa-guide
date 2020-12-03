package com.example.jpa.demo.db;

import com.example.jpa.demo.core.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class UserInfo extends BaseEntity {
	private String name;
	private Integer ages;
	private String lastName;
	private String emailAddress;
	private String telephone;
}

