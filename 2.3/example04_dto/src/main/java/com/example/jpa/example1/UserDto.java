package com.example.jpa.example1;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
//@Value
public class UserDto {
//    public UserDto(String name, String email) {
//        this.name = name;
//        this.email = email;
//    }
//
//    public UserDto() {
//    }

    private String name,email;

//    public String getName() {
//        return name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
}
