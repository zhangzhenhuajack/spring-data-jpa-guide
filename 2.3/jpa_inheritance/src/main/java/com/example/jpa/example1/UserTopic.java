package com.example.jpa.example1;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "user_topic")
@Access(AccessType.FIELD)
@Data
public class UserTopic {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "title", nullable = true, length = 200)
	private String title;
	@Basic
	@Column(name = "create_user_id", nullable = true)
	private Integer createUserId;
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "content", nullable = true, length = -1)
	@Lob
	private String content;
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "image", nullable = true)
	@Lob
	private byte[] image;
	@Basic
	@Column(name = "create_time", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Basic
	@Column(name = "create_date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date createDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "topic_type")
	private Type type;
	@Transient
	private String transientSimple;
	//非数据库映射字段，业务类型的字段
	public String getTransientSimple() {
		return title + "auto:jack" + type;
	}

	//有一个枚举类，主题的类型
	public enum Type {
		EN("英文"), CN("中文");
		private final String des;
		Type(String des) {
			this.des = des;
		}
	}
}
