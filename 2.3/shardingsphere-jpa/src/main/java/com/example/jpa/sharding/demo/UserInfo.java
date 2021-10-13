package com.example.jpa.sharding.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
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
    private Boolean deleted;
}
