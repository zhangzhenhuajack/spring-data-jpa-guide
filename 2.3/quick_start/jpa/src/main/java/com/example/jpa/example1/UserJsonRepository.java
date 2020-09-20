package com.example.jpa.example1;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJsonRepository extends JpaRepository<UserJson,Long> {
}
