package com.example.jpa.example1;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private String name,email,idCard;
}
