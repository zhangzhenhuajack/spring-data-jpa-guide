package com.example.jpa.demo.db;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

/**
 * @author jack
 */
@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TimeTest {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private  Long id;
    private  Instant dateType;
    private Instant dateTimeType;
    private Instant timeStampType;
    private Instant timeType;
}
