package com.example.jpa.demo.service.dto;

import com.example.jpa.demo.db.Address;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserInfoDto {
    private String name;
    private Long id;
    private List<Address> addressList;
}
