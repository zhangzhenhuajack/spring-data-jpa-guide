package com.example.jpa.example1;
import org.springframework.data.repository.PagingAndSortingRepository;
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
}
