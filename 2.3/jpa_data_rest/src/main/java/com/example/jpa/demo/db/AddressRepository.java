package com.example.jpa.demo.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface AddressRepository extends JpaRepository<Address, Long>{
    Page<Address> findAll(Pageable pageable);
    @RestResource(
            exported = true,//是否暴漏给Search
            path = "findCities",//Search后面的path路径
            rel = "cities"//资源名字
    )
    Page<Address> findByAddress(@Param("address") String address, Pageable pageable);
}
