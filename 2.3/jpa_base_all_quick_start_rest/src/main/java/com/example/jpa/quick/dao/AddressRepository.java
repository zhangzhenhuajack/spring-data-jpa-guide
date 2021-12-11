package com.example.jpa.quick.dao;

import com.example.jpa.quick.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
