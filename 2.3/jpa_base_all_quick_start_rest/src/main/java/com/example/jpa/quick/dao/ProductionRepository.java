package com.example.jpa.quick.dao;

import com.example.jpa.quick.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionRepository extends JpaRepository<Production,Long> {
}
