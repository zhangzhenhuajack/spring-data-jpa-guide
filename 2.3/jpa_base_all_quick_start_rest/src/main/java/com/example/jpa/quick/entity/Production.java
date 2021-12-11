package com.example.jpa.quick.entity;

import com.example.jpa.quick.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author jack
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Production extends BaseEntity {
    private String title;
}
