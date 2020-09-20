package com.example.jpa.example1;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"createDate","email"})
public class UserJson {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@JsonProperty("my_name")
	private String name;
	private Instant createDate;
	@JsonFormat(timezone ="GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Date updateDate;
	private String email;
	@JsonIgnore
	private String sex;
	@JsonCreator
	public UserJson(@JsonProperty("email") String email) {
		System.out.println("其它业务逻辑");
		this.email = email;
	}
	@Transient
	@JsonAnySetter
	private Map<String,Object> other = new HashMap<>();

	@JsonAnyGetter
	public Map<String, Object> getOther() {
		return other;
	}
}
