package com.example.webflux_jpa_mysql.core;

import com.sun.org.apache.xml.internal.utils.SerializableLocatorImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Table("user")
@NoArgsConstructor
public class UserEntity  implements Serializable {
    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;
    public String name;
    public Integer age;
    public String email;

    public UserEntity(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
