package com.example.jpa.example1.book;
import lombok.Data;
import javax.persistence.*;
@Entity(name="book")
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table
public class Book {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private String title;
}