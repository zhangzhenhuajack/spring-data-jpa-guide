package com.example.jpa.example1;
import lombok.*;
import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoomRelation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date createTime,udpateTime;
	@ManyToOne
	private Room room;
	@ManyToOne
	private User user;
}
