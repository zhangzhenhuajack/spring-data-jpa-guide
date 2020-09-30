package com.example.jpa.example1.customized;

import com.example.jpa.example1.User;

public interface CustomizedUserRepository {
    User logicallyDelete(User user);
}
