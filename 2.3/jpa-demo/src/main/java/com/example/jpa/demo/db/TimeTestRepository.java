package com.example.jpa.demo.db;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeTestRepository extends CrudRepository<TimeTest,Long> {
    @Query(value = "select * from time_test where name = :#{#name}",nativeQuery = true)
    List<TimeTest> findByNameAbc(@Param(value = "name") String name);
}
