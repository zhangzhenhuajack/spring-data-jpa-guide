package com.example.jpa.example1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserOnlyNameEmailDto {
    private String name,email;

    public UserOnlyNameEmailDto() {
    }
}

