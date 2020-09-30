package com.example.jpa.example1.customized;

import com.example.jpa.example1.User;

import javax.persistence.EntityManager;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {
    private EntityManager entityManager;

    public CustomizedUserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User logicallyDelete(User user) {
        user.setDeleted(true);
        return entityManager.merge(user);
    }
}
