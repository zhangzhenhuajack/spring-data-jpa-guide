package com.example.jpa.example1.book;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class BlueBook extends Book{
	private String blueMark;
}
