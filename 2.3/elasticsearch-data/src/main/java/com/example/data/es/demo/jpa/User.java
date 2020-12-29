package com.example.data.es.demo.jpa;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
}
