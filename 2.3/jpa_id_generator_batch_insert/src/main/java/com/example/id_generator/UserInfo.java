package com.example.id_generator;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
public class UserInfo  implements Persistable<Integer> {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
// 加上IDENTITY batch就不起作用了
    public Integer id;
    public String name;
    @Version
    private Long version; //必须要带上version字段，解决detached entity passed to persist异常问题
    public UserInfo(String s, int i) {
        this.name=s;
        this.id=i;
        this.defaultNew=true; //解决默认会产生select 的sql的问题
    }

    public UserInfo() {

    }
    @Override
    public Integer getId() {
        return this.id;
    }

    @Transient
    boolean defaultNew = false;

    @Override
    public boolean isNew() {
        return null == getId() || defaultNew;
    }

    public void setDefaultNew(boolean defaultNew) {
        this.defaultNew = defaultNew;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
