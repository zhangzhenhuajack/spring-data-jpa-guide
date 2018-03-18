package com.example.example2.repository;

import com.example.example2.User;
import org.springframework.data.repository.CrudRepository;

public interface UserCrudRepository extends CrudRepository<User,Long> {
}
