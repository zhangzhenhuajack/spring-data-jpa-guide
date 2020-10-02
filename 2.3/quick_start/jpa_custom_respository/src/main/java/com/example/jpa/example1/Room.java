package com.example.jpa.example1;
import lombok.*;
import javax.persistence.*;
import java.util.List;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToMany(mappedBy = "room")
	private List<UserRoomRelation> userRoomRelations;
}
