package com.example.jpa.example1.base;

import com.example.jpa.example1.User;

import javax.persistence.PrePersist;

public class UserListener {
    @PrePersist
    public void prePersist(User user) {
        //通过一些逻辑计算年龄；
        user.calculationAge();
    }
}
