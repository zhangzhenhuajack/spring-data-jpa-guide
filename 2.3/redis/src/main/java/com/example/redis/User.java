package com.example.redis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class User implements Serializable {
    private String name,email;
    @JsonProperty(value = "abc",index = 1)
    private Address address;
    private Map<String,Address> addressMap;
}
