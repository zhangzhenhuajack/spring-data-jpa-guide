package com.example.jpa.demo.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface AddressRepository extends JpaRepository<Address, Long>{
    //通过@Query注解自定的JPQL或是Navicat SQL
//    @Query(value = "FROM Address where deleted=false ")
    Page<Address> findAll(Pageable pageable);
    //自定义query method defined
    Page<Address> findByAddress(@Param("address") String address, Pageable pageable);
}
