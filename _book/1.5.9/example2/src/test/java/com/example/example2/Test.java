package com.example.example2;

import org.springframework.data.repository.Repository;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.UUID;

public class Test {
}

@Entity
class Person {
	@Id
	UUID id;
	String firstname, lastname;
	Address address;
	@Entity
	static class Address {
		String zipCode, city, street;
	}
}
interface PersonRepository extends Repository<Person, UUID> {
	Collection<Person> findByLastname(String lastname);
}